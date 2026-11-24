package com.setupforge.setupforge_backend.produto.controller;


import com.setupforge.setupforge_backend.produto.dto.request.ProdutoRequest;
import com.setupforge.setupforge_backend.produto.dto.response.ProdutoResponse;
import com.setupforge.setupforge_backend.produto.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
public class ProdutoController {


    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponse> save(@Valid @RequestBody ProdutoRequest produtoRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produtoRequest));
    }


    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> buscarTudo() {

        return ResponseEntity.ok().body(produtoService.buscarTudo());
    }


    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProdutoResponse>> buscarPeloNome(@PathVariable("nome")String nome) {

        return ResponseEntity.ok().body(produtoService.buscarPeloNome(nome));
    }


}
