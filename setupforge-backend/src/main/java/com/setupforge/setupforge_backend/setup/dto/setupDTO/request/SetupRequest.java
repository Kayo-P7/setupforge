package com.setupforge.setupforge_backend.setup.dto.setupDTO.request;

import com.setupforge.setupforge_backend.setup.enums.TipoMontagem;

import java.math.BigDecimal;

public record SetupRequest(
        String nome,
        String descricao,
        BigDecimal orcamento,
        TipoMontagem tipoMontagem
) {
}
