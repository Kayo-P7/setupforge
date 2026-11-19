package com.setupforge.setupforge_backend.category.controller;

import com.setupforge.setupforge_backend.category.dto.request.CategoriaRequest;
import com.setupforge.setupforge_backend.category.dto.response.CategoriaResponse;
import com.setupforge.setupforge_backend.category.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponse> save(@RequestBody CategoriaRequest categoriaRequest) {

        return ResponseEntity.ok().body(categoriaService.save(categoriaRequest));
    }


}
