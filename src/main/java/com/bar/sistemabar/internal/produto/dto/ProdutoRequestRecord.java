package com.bar.sistemabar.internal.produto.dto;

import com.bar.sistemabar.internal.produto.entity.StatusProduto;
import com.bar.sistemabar.internal.produto.entity.TipoLancamentoProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProdutoRequestRecord(

        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotBlank(message = "A descrição é obrigatória.")
        String descricao,

        @NotNull(message = "O preço é obrigatório.")
        @Positive(message = "O preço deve ser maior que zero.")
        Double preco,

        @NotNull(message = "O controle de estoque é obrigatório.")
        Boolean controlaEstoque,

        @NotNull(message = "A quantidade em estoque é obrigatória.")
        @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa.")
        Integer quantidadeEstoque,

        @NotNull(message = "O tipo de lançamento é obrigatório.")
        TipoLancamentoProduto tipoLancamento,

        @NotNull(message = "O status é obrigatório.")
        StatusProduto status,

        @NotNull(message = "A categoria é obrigatória.")
        Long categoriaId

) {
}