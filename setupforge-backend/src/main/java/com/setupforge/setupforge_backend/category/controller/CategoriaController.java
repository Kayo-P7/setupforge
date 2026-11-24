
package com.setupforge.setupforge_backend.category.controller;

import com.setupforge.setupforge_backend.category.dto.request.CategoriaRequest;
import com.setupforge.setupforge_backend.category.dto.response.CategoriaResponse;
import com.setupforge.setupforge_backend.category.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Gerenciamento de categorias de produtos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    @Operation(summary = "Cadastrar categoria",
            description = "Cadastra uma nova categoria de produtos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<CategoriaResponse> save(
            @Valid @RequestBody CategoriaRequest categoriaRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.save(categoriaRequest));
    }

    @GetMapping
    @Operation(summary = "Listar categorias",
            description = "Retorna todas as categorias cadastradas.")
    @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso")
    public ResponseEntity<List<CategoriaResponse>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID",
            description = "Busca uma categoria utilizando seu identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<CategoriaResponse> findById(
            @Parameter(description = "ID da categoria", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria",
            description = "Atualiza os dados de uma categoria existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<CategoriaResponse> update(
            @Parameter(description = "ID da categoria", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest categoriaRequest) {

        return ResponseEntity.ok(categoriaService.update(id, categoriaRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir categoria",
            description = "Remove uma categoria pelo seu identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da categoria", example = "1")
            @PathVariable Long id) {

        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
