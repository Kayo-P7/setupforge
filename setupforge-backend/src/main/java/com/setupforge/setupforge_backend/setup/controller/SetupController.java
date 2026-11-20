
package com.setupforge.setupforge_backend.setup.controller;

import com.setupforge.setupforge_backend.setup.dto.setupDTO.request.SetupRequest;
import com.setupforge.setupforge_backend.setup.dto.setupDTO.response.SetupResponse;
import com.setupforge.setupforge_backend.setup.service.SetupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/setup")
@Tag(name = "Setups", description = "Gerenciamento das montagens de computadores")
public class SetupController {

    private final SetupService setupService;

    @PostMapping
    @Operation(summary = "Criar setup", description = "Cria uma nova montagem de computador.")
    @ApiResponse(responseCode = "201", description = "Setup criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<SetupResponse> criarSetup(
            @Valid @RequestBody SetupRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(setupService.criarSetup(request));
    }

    @GetMapping
    @Operation(summary = "Listar setups", description = "Retorna todos os setups cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<SetupResponse>> listarSetups() {

        return ResponseEntity.ok(setupService.listarSetups());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar setup", description = "Busca uma montagem pelo identificador.")
    @ApiResponse(responseCode = "200", description = "Setup encontrado")
    @ApiResponse(responseCode = "404", description = "Setup não encontrado")
    public ResponseEntity<SetupResponse> buscarPorId(
            @Parameter(description = "ID do setup", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(setupService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir setup", description = "Exclui uma montagem pelo identificador.")
    @ApiResponse(responseCode = "204", description = "Setup removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Setup não encontrado")
    public ResponseEntity<Void> removerSetup(
            @Parameter(description = "ID do setup", example = "1")
            @PathVariable Long id) {

        setupService.removerSetup(id);

        return ResponseEntity.noContent().build();
    }
}
