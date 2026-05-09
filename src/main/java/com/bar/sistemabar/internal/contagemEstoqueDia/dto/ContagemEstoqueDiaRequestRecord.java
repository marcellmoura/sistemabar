package com.bar.sistemabar.internal.contagemEstoqueDia.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ContagemEstoqueDiaRequestRecord(

        @NotNull(message = "O ID do produto é obrigatório")
        Long produtoId,

        @NotNull(message = "O ID do movimento do dia é obrigatório")
        Long movimentoDiaId,

        @NotNull(message = "A quantidade inicial é obrigatória")
        @Min(value = 0, message = "A quantidade inicial não pode ser negativa")
        Integer quantidadeInicial

) {
}