package com.bar.sistemabar.internal.contagemEstoqueDia.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ContagemFinalRequestRecord(

        @NotNull(message = "A quantidade final é obrigatória")
        @Min(value = 0, message = "A quantidade final não pode ser negativa")
        Integer quantidadeFinal

) {
}