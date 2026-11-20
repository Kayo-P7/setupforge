package com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request;

import com.setupforge.setupforge_backend.setup.model.Setup;

import java.math.BigDecimal;

public record SetupProdutoRequest(

        Long setupId,
        Long produtoId,
        Integer quantidade
) {
}
