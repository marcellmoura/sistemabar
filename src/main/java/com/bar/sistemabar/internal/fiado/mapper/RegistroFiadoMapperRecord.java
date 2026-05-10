package com.bar.sistemabar.internal.fiado.mapper;

import com.bar.sistemabar.internal.fiado.dto.RegistroFiadoResponseRecord;
import com.bar.sistemabar.internal.fiado.entity.RegistroFiadoEntity;

import java.util.List;

public record RegistroFiadoMapperRecord() {

    public static RegistroFiadoResponseRecord entityToResponse(
            RegistroFiadoEntity entity
    ) {

        return new RegistroFiadoResponseRecord(
                entity.getId(),
                entity.getPessoaFiado().getId(),
                entity.getPessoaFiado().getNome(),
                entity.getProduto().getId(),
                entity.getProduto().getNome(),
                entity.getUsuario().getId(),
                entity.getUsuario().getNome(),
                entity.getMovimentoDia().getId(),
                entity.getQuantidade(),
                entity.getValor(),
                entity.getDataHora(),
                entity.getObservacao(),
                entity.getStatus()
        );
    }

    public static List<RegistroFiadoResponseRecord> entityListToResponseList(
            List<RegistroFiadoEntity> entities
    ) {

        return entities.stream()
                .map(RegistroFiadoMapperRecord::entityToResponse)
                .toList();
    }
}
