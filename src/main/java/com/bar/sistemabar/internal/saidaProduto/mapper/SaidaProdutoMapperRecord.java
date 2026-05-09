package com.bar.sistemabar.internal.saidaProduto.mapper;

import com.bar.sistemabar.internal.saidaProduto.dto.SaidaProdutoResponseRecord;
import com.bar.sistemabar.internal.saidaProduto.entity.SaidaProdutoEntity;

import java.util.List;

public record SaidaProdutoMapperRecord() {

    public static SaidaProdutoResponseRecord entityToResponseRecord(SaidaProdutoEntity entity) {

        return new SaidaProdutoResponseRecord(
                entity.getId(),
                entity.getProduto().getId(),
                entity.getProduto().getNome(),
                entity.getUsuario().getId(),
                entity.getUsuario().getNome(),
                entity.getQuantidade(),
                entity.getTipoSaida(),
                entity.getDataHora()
        );
    }

    public static List<SaidaProdutoResponseRecord> entityListToResponseList(List<SaidaProdutoEntity> entities) {

        return entities.stream()
                .map(SaidaProdutoMapperRecord::entityToResponseRecord)
                .toList();
    }
}