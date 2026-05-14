package com.bar.sistemabar.internal.caixa.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CaixaRequestRecord(

        @NotNull(message = "O movimento do dia é obrigatório.")
        Long movimentoDiaId,

        @NotNull(message = "O usuário é obrigatório.")
        Long usuarioId,

        @NotNull(message = "O valor informado é obrigatório.")
        @PositiveOrZero(message = "O valor informado não pode ser negativo.")
        Double valorInformado,

        @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres.")
        String observacao

) {
}
