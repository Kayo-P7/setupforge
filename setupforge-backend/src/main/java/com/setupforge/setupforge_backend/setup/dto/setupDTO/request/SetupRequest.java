package com.setupforge.setupforge_backend.setup.dto.setupDTO.request;

import com.setupforge.setupforge_backend.setup.enums.TipoMontagem;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SetupRequest(
        @NotBlank(message = "O nome do setup é obrigatório.")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
        String nome,

        @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.")
        String descricao,

        @NotNull(message = "O orçamento é obrigatório.")
        @DecimalMin(value = "0.00", message = "O orçamento não pode ser negativo.")
        BigDecimal orcamento,

        @NotNull(message = "O tipo de montagem é obrigatório.")
        TipoMontagem tipoMontagem
) {
}
