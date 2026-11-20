package com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request;

import jakarta.validation.constraints.NotBlank;

public record AtualizarQuantidadeRequest(
        @NotBlank
        Integer quantidade
) {
}
