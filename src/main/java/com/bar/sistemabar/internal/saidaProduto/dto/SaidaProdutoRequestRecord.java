package com.bar.sistemabar.internal.saidaProduto.dto;

import com.bar.sistemabar.internal.saidaProduto.entity.TipoSaidaProduto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaidaProdutoRequestRecord(

        @NotNull(message = "O produto é obrigatório.")
        Long produtoId,

        @NotNull(message = "O usuário é obrigatório.")
        Long usuarioId,

        @NotNull(message = "A quantidade é obrigatória.")
        @Positive(message = "A quantidade deve ser maior que zero.")
        Integer quantidade,

        @NotNull(message = "O tipo de saída é obrigatório.")
        TipoSaidaProduto tipoSaida

) {
}