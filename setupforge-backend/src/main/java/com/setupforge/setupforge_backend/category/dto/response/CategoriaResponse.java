package com.setupforge.setupforge_backend.category.dto.response;

import com.setupforge.setupforge_backend.category.model.Categoria;

public record CategoriaResponse(
        Long id,
        String nome,
        String descricao
) {
}
