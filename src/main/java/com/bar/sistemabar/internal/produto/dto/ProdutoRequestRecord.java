package com.bar.sistemabar.internal.produto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoRequestRecord(

        @NotBlank(message = "O nome do produto é obrigatório")
        String nome,

        @NotNull(message = "O preço do produto é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        Double preco,

        @NotNull(message = "Informe se o produto controla estoque")
        Boolean controlaEstoque,

        @NotBlank(message = "O tipo de lançamento é obrigatório")
        String tipoLancamento,

        @NotBlank(message = "O status é obrigatório")
        String status,

        @NotNull(message = "A categoria é obrigatória")
        Long categoriaId
) {
}