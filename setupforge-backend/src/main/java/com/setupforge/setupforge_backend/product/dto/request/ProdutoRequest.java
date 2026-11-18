package com.setupforge.setupforge_backend.product.dto.request;

import com.setupforge.setupforge_backend.category.model.Categoria;

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
