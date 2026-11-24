package com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request;

import com.setupforge.setupforge_backend.setup.model.Setup;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SetupProdutoRequest(

        @NotNull(message = "O setup é obrigatório.")
        @Positive(message = "O setup deve ser válido.")
        Long setupId,

        @NotNull(message = "O produto é obrigatório.")
        @Positive(message = "O produto deve ser válido.")
        Long produtoId,

        @NotNull(message = "A quantidade é obrigatória.")
        @Positive(message = "A quantidade deve ser maior que zero.")
        Integer quantidade
) {
}
