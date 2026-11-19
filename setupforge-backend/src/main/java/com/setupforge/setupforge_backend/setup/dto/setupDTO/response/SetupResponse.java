package com.setupforge.setupforge_backend.setup.dto.setupDTO.response;

import com.setupforge.setupforge_backend.setup.enums.TipoMontagem;

import java.math.BigDecimal;

public record SetupResponse(
        String nome,
        String descricao,
        BigDecimal orcamento,
        TipoMontagem tipoMontagem
) {
}
