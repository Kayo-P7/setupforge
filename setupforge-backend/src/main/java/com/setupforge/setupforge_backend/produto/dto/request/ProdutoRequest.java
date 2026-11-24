package com.setupforge.setupforge_backend.produto.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProdutoRequest(
        @NotBlank(message = "O nome do produto é obrigatório.")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
        String nome,

        @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.")
        String descricao,

        @NotBlank(message = "A marca do produto é obrigatória.")
        @Size(max = 100, message = "A marca deve ter no máximo 100 caracteres.")
        String marca,

        @NotNull(message = "O preço é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
        BigDecimal preco,

        @Size(max = 500, message = "A URL da imagem deve ter no máximo 500 caracteres.")
        String imagemUrl,

        @NotNull(message = "A categoria é obrigatória.")
        @Positive(message = "A categoria deve ser válida.")
        Long categoriaId
) {
}
