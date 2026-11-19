package com.setupforge.setupforge_backend.setup.controller;


import com.setupforge.setupforge_backend.setup.service.SetupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/setupProduto")
public class SetupProdutoController {


    private final SetupService setupService;

    @GetMapping("/id/{setupId}")
    public BigDecimal valorTotal(@PathVariable("setupId") Long setupId) {

        return setupService.valorTotal(setupId);
    }


}
