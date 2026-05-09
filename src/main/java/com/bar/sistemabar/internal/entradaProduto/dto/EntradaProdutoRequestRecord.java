package com.bar.sistemabar.internal.entradaProduto.dto;

import com.bar.sistemabar.internal.entradaProduto.entity.TipoEntradaProduto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EntradaProdutoRequestRecord(

        @NotNull(message = "O produto é obrigatório.")
        Long produtoId,

        @NotNull(message = "O usuário é obrigatório.")
        Long usuarioId,

        @NotNull(message = "A quantidade é obrigatória.")
        @Positive(message = "A quantidade deve ser maior que zero.")
        Integer quantidade,

        @NotNull(message = "O tipo de entrada é obrigatório.")
        TipoEntradaProduto tipoEntrada

) {
}