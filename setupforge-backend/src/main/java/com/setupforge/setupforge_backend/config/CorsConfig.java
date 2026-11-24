package com.setupforge.setupforge_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private static final List<String> LOCAL_ORIGINS = List.of(
            "http://localhost:8081",
            "http://localhost:8082"
    );

    private final List<String> allowedOrigins;

    public CorsConfig(
            @Value("${CORS_ALLOWED_ORIGINS:http://localhost:8081,http://localhost:8082}")
            String allowedOrigins
    ) {
        this.allowedOrigins = Stream.concat(
                        LOCAL_ORIGINS.stream(),
                        Arrays.stream(allowedOrigins.split(","))
                )
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .map(CorsConfig::validarOrigem)
                .distinct()
                .toList();
    }

    private static String validarOrigem(String origin) {
        if (origin.contains("*") || origin.equalsIgnoreCase("null")) {
            throw new IllegalArgumentException("CORS_ALLOWED_ORIGINS aceita somente origens explícitas.");
        }

        URI uri;
        try {
            uri = URI.create(origin);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Uma origem configurada em CORS_ALLOWED_ORIGINS é inválida.", exception);
        }

        boolean originOnly = ("http".equals(uri.getScheme()) || "https".equals(uri.getScheme()))
                && uri.getHost() != null
                && uri.getUserInfo() == null
                && (uri.getPath().isEmpty() || "/".equals(uri.getPath()))
                && uri.getQuery() == null
                && uri.getFragment() == null;
        if (!originOnly) {
            throw new IllegalArgumentException("CORS_ALLOWED_ORIGINS deve conter origens HTTP/HTTPS sem caminho.");
        }

        return uri.getScheme() + "://" + uri.getRawAuthority();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins.toArray(String[]::new))
                .allowedMethods(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                )
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
