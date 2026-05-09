package com.bar.sistemabar.internal.produto.dto;

import com.bar.sistemabar.internal.produto.entity.StatusProduto;
import com.bar.sistemabar.internal.produto.entity.TipoLancamentoProduto;

public record ProdutoResponseRecord(

        Long id,
        String nome,
        String descricao,
        Double preco,
        Boolean controlaEstoque,
        Integer quantidadeEstoque,
        TipoLancamentoProduto tipoLancamento,
        StatusProduto status,
        Long categoriaId,
        String categoriaNome

) {
}