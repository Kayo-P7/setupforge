package com.setupforge.setupforge_backend.product.dto.response;

import com.setupforge.setupforge_backend.category.model.Categoria;

import java.math.BigDecimal;

public record ProdutoResponse(
        String nome,
        String descricao,
        String marca,
        BigDecimal preco,
        String imagemUrl,
        String categoriaNome,
        String categoriaDescricao
) {
}
