package com.bar.sistemabar.internal.fiado.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RegistroFiadoRequestRecord(

        @NotNull(message = "A pessoa do fiado é obrigatória.")
        Long pessoaFiadoId,

        @NotNull(message = "O produto é obrigatório.")
        Long produtoId,

        @NotNull(message = "O usuário é obrigatório.")
        Long usuarioId,

        @NotNull(message = "A quantidade é obrigatória.")
        @Positive(message = "A quantidade deve ser maior que zero.")
        Integer quantidade,

        @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres.")
        String observacao

) {
}
