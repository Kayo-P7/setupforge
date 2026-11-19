package com.setupforge.setupforge_backend.produto.dto.response;

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
