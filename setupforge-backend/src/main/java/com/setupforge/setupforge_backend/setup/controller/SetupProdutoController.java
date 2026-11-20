
package com.setupforge.setupforge_backend.setup.controller;

import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request.AtualizarQuantidadeRequest;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request.SetupProdutoRequest;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.response.SetupProdutoResponse;
import com.setupforge.setupforge_backend.setup.service.SetupProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/setup/{setupId}/produtos")
@Tag(name = "Produtos do Setup", description = "Gerenciamento dos produtos associados a uma montagem")
public class SetupProdutoController {

    private final SetupProdutoService setupProdutoService;

    @PostMapping
    @Operation(summary = "Adicionar produto", description = "Adiciona um produto a uma montagem.")
    @ApiResponse(responseCode = "201", description = "Produto adicionado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou setupId divergente")
    public ResponseEntity<SetupProdutoResponse> adicionarProduto(
            @Parameter(description = "ID do setup", example = "1")
            @PathVariable Long setupId,
            @Valid @RequestBody SetupProdutoRequest request) {

        if (!setupId.equals(request.setupId())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(setupProdutoService.adicionarProduto(request));
    }

    @GetMapping
    @Operation(summary = "Listar produtos", description = "Lista todos os produtos de uma montagem.")
    @ApiResponse(responseCode = "200", description = "Produtos retornados com sucesso")
    @ApiResponse(responseCode = "404", description = "Setup não encontrado")
    public ResponseEntity<List<SetupProdutoResponse>> listarProdutos(
            @Parameter(description = "ID do setup", example = "1")
            @PathVariable Long setupId) {

        return ResponseEntity.ok(
                setupProdutoService.listarProdutos(setupId)
        );
    }

    @PatchMapping("/{itemId}")
    @Operation(summary = "Atualizar quantidade", description = "Atualiza a quantidade de um produto na montagem.")
    @ApiResponse(responseCode = "200", description = "Quantidade atualizada")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado neste setup")
    public ResponseEntity<SetupProdutoResponse> atualizarQuantidade(
            @Parameter(description = "ID do setup", example = "1")
            @PathVariable Long setupId,
            @Parameter(description = "ID do item associado ao setup", example = "1")
            @PathVariable Long itemId,
            @Valid @RequestBody AtualizarQuantidadeRequest request) {

        return ResponseEntity.ok(
                setupProdutoService.atualizarQuantidade(
                        setupId,
                        itemId,
                        request.quantidade()
                )
        );
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Remover produto", description = "Remove um produto de uma montagem.")
    @ApiResponse(responseCode = "204", description = "Produto removido")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado neste setup")
    public ResponseEntity<Void> removerProduto(
            @PathVariable Long setupId,
            @PathVariable Long itemId) {

        setupProdutoService.removerProduto(setupId, itemId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total")
    @Operation(summary = "Consultar valor total", description = "Calcula o valor total dos produtos de uma montagem.")
    @ApiResponse(responseCode = "200", description = "Valor total calculado")
    public ResponseEntity<BigDecimal> valorTotal(
            @PathVariable Long setupId) {

        return ResponseEntity.ok(
                setupProdutoService.valorTotal(setupId)
        );
    }
}
