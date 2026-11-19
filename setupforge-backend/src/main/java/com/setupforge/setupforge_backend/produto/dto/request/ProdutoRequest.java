package com.setupforge.setupforge_backend.produto.dto.request;

import java.math.BigDecimal;

public record ProdutoRequest(
        String nome,
        String descricao,
        String marca,
        BigDecimal preco,
        String imagemUrl,
        Long categoriaId
) {
}
