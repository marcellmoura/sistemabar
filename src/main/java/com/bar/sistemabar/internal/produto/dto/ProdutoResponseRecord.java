package com.bar.sistemabar.internal.produto.dto;

public record ProdutoResponseRecord(
        Long id,
        String nome,
        Double preco,
        Integer quantidadeEstoque,
        Boolean controlaEstoque,
        String tipoLancamento,
        String status,
        Long categoriaId,
        String categoriaNome
) {
}