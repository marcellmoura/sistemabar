package com.bar.sistemabar.internal.movimentoDia.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MovimentoDiaRequestRecord(

        @NotNull(message = "O troco inicial é obrigatório")
        BigDecimal trocoInicial,

        @NotNull(message = "O usuário responsável é obrigatório")
        Long usuarioResponsavelId

) {
}