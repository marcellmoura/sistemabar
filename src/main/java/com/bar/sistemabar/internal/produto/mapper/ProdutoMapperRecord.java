package com.bar.sistemabar.internal.produto.mapper;

import com.bar.sistemabar.internal.categoria.entity.CategoriaEntity;
import com.bar.sistemabar.internal.produto.dto.ProdutoRequestRecord;
import com.bar.sistemabar.internal.produto.dto.ProdutoResponseRecord;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;

import java.util.List;

public record ProdutoMapperRecord() {

    public static ProdutoEntity paraEntity(ProdutoRequestRecord request, CategoriaEntity categoria) {

        return new ProdutoEntity(
                request.nome(),
                request.preco(),
                0,
                request.controlaEstoque(),
                request.tipoLancamento(),
                request.status(),
                categoria
        );
    }

    public static ProdutoResponseRecord paraResponse(ProdutoEntity entity) {

        return new ProdutoResponseRecord(
                entity.getId(),
                entity.getNome(),
                entity.getPreco(),
                entity.getQuantidadeEstoque(),
                entity.getControlaEstoque(),
                entity.getTipoLancamento(),
                entity.getStatus(),
                entity.getCategoria().getId(),
                entity.getCategoria().getNome()
        );
    }

    public static List<ProdutoResponseRecord> paraListaResponse(List<ProdutoEntity> entities) {

        return entities.stream()
                .map(ProdutoMapperRecord::paraResponse)
                .toList();
    }
}