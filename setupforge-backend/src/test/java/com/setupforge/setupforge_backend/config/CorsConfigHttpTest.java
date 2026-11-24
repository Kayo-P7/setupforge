package com.setupforge.setupforge_backend.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(
        properties = "CORS_ALLOWED_ORIGINS=https://frontend.example,https://preview.example"
)
@ActiveProfiles("test")
class CorsConfigHttpTest {

    private static final String ORIGIN_LOCAL_8081 = "http://localhost:8081";
    private static final String ORIGIN_LOCAL_8082 = "http://localhost:8082";
    private static final String ORIGIN_CONFIGURED = "https://frontend.example";
    private static final String ORIGIN_PREVIEW = "https://preview.example";
    private static final String ORIGIN_NOT_CONFIGURED = "https://untrusted.example";

    @Autowired
    private WebApplicationContext context;

    private MockMvc http;

    @BeforeEach
    void setUp() {
        http = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void permitePreflightParaOrigemLocal8081() throws Exception {
        assertPreflightAllowed(ORIGIN_LOCAL_8081);
    }

    @Test
    void permitePreflightParaOrigemLocal8082() throws Exception {
        assertPreflightAllowed(ORIGIN_LOCAL_8082);
    }

    @Test
    void permitePreflightParaOrigemConfiguradaPorVariavel() throws Exception {
        assertPreflightAllowed(ORIGIN_CONFIGURED);
    }

    @Test
    void permiteMaisDeUmaOrigemConfiguradaPorVariavel() throws Exception {
        assertPreflightAllowed(ORIGIN_PREVIEW);
    }

    @Test
    void rejeitaPreflightDeOrigemNaoConfigurada() throws Exception {
        MvcResult response = preflight(ORIGIN_NOT_CONFIGURED);

        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.getResponse().getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)).isNull();
    }

    @Test
    void permiteRequisicaoGetDeOrigemLocalERejeitaOutraOrigem() throws Exception {
        MvcResult allowed = http.perform(get("/categorias").header(HttpHeaders.ORIGIN, ORIGIN_LOCAL_8081))
                .andReturn();

        assertThat(allowed.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(allowed.getResponse().getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN))
                .isEqualTo(ORIGIN_LOCAL_8081);

        MvcResult denied = http.perform(get("/categorias").header(HttpHeaders.ORIGIN, ORIGIN_NOT_CONFIGURED))
                .andReturn();

        assertThat(denied.getResponse().getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(denied.getResponse().getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)).isNull();
    }

    @Test
    void rejeitaWildcardNaListaDeOrigens() {
        assertThrows(IllegalArgumentException.class,
                () -> new CorsConfig("https://frontend.example,*"));
    }

    @Test
    void retornaBadRequestParaDadosDeCategoriaInvalidos() throws Exception {
        MvcResult response = http.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andReturn();

        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void retornaNotFoundParaCategoriaInexistente() throws Exception {
        MvcResult response = http.perform(get("/categorias/99999999")).andReturn();

        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private void assertPreflightAllowed(String origin) throws Exception {
        MvcResult response = preflight(origin);

        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getResponse().getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)).isEqualTo(origin);
        assertThat(response.getResponse().getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS))
                .contains("GET", "OPTIONS");
        assertThat(response.getResponse().getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS))
                .containsIgnoringCase("content-type");
    }

    private MvcResult preflight(String origin) throws Exception {
        return http.perform(options("/categorias")
                        .header(HttpHeaders.ORIGIN, origin)
                        .header("Access-Control-Request-Method", "GET")
                        .header("Access-Control-Request-Headers", "content-type"))
                .andReturn();
    }
}
