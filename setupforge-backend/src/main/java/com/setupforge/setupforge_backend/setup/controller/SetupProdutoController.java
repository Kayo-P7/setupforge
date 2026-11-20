
package com.setupforge.setupforge_backend.setup.controller;

import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request.AtualizarQuantidadeRequest;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request.SetupProdutoRequest;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.response.SetupProdutoResponse;
import com.setupforge.setupforge_backend.setup.service.SetupProdutoService;
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
public class SetupProdutoController {

    private final SetupProdutoService setupProdutoService;

    @PostMapping
    public ResponseEntity<SetupProdutoResponse> adicionarProduto(
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
    public ResponseEntity<List<SetupProdutoResponse>> listarProdutos(
            @PathVariable Long setupId) {

        return ResponseEntity.ok(
                setupProdutoService.listarProdutos(setupId)
        );
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<SetupProdutoResponse> atualizarQuantidade(
            @PathVariable Long setupId,
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
    public ResponseEntity<Void> removerProduto(
            @PathVariable Long setupId,
            @PathVariable Long itemId) {

        setupProdutoService.removerProduto(setupId, itemId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> valorTotal(
            @PathVariable Long setupId) {

        return ResponseEntity.ok(
                setupProdutoService.valorTotal(setupId)
        );
    }
}
