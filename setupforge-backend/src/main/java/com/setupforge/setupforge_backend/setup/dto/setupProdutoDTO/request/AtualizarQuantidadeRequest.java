package com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarQuantidadeRequest(
        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
        Integer quantidade
) {
}
