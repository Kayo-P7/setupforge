package com.setupforge.setupforge_backend.setup.controller;


import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request.SetupProdutoRequest;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.response.SetupProdutoResponse;
import com.setupforge.setupforge_backend.setup.service.SetupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/setupProduto")
public class SetupProdutoController {


    private final SetupService setupService;


    @PostMapping
    public ResponseEntity<SetupProdutoResponse> saveSetupProduto(@RequestBody SetupProdutoRequest setupProdutoRequest) {

        return ResponseEntity.ok().body(setupService.saveSetupProduto(setupProdutoRequest));
    }

    @GetMapping("/id/{setupId}")
    public BigDecimal valorTotal(@PathVariable("setupId") Long setupId) {

        return setupService.valorTotal(setupId);
    }


}
