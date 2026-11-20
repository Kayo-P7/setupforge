package com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.response;

import java.math.BigDecimal;

public record SetupProdutoResponse(
        Long id,
        Long produtoId,
        String produtoNome,
        String marca,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
}
