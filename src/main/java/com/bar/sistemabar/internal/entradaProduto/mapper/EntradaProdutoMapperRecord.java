package com.bar.sistemabar.internal.entradaProduto.mapper;

import com.bar.sistemabar.internal.entradaProduto.dto.EntradaProdutoRequestRecord;
import com.bar.sistemabar.internal.entradaProduto.dto.EntradaProdutoResponseRecord;
import com.bar.sistemabar.internal.entradaProduto.entity.EntradaProdutoEntity;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;

import java.time.LocalDateTime;
import java.util.List;

public record EntradaProdutoMapperRecord() {

    public static EntradaProdutoEntity paraEntity(
            EntradaProdutoRequestRecord request,
            ProdutoEntity produto,
            UsuarioEntity usuario
    ) {
        return new EntradaProdutoEntity(
                request.quantidade(),
                request.tipoEntrada(),
                LocalDateTime.now(),
                produto,
                usuario
        );
    }

    public static EntradaProdutoResponseRecord paraResponse(EntradaProdutoEntity entity) {
        return new EntradaProdutoResponseRecord(
                entity.getId(),
                entity.getProduto().getId(),
                entity.getProduto().getNome(),
                entity.getUsuario().getId(),
                entity.getUsuario().getNome(),
                entity.getQuantidade(),
                entity.getTipoEntrada(),
                entity.getDataHora()
        );
    }

    public static List<EntradaProdutoResponseRecord> paraListaResponse(List<EntradaProdutoEntity> entities) {
        return entities.stream()
                .map(EntradaProdutoMapperRecord::paraResponse)
                .toList();
    }
}