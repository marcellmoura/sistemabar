package com.bar.sistemabar.internal.caixa.mapper;

import com.bar.sistemabar.internal.caixa.dto.CaixaResponseRecord;
import com.bar.sistemabar.internal.caixa.entity.CaixaEntity;

import java.util.List;

public record CaixaMapperRecord() {

    public static CaixaResponseRecord entityToResponse(CaixaEntity entity) {

        return new CaixaResponseRecord(
                entity.getId(),
                entity.getMovimentoDia().getId(),
                entity.getUsuario().getId(),
                entity.getUsuario().getNome(),
                entity.getValorVendidoCalculado(),
                entity.getValorFiadoAberto(),
                entity.getValorFiadoPago(),
                entity.getValorTotalFiado(),
                entity.getValorRecebidoEstimado(),
                entity.getValorInformado(),
                entity.getDiferenca(),
                entity.getDataHoraConferencia(),
                entity.getObservacao(),
                entity.getStatus()
        );
    }

    public static List<CaixaResponseRecord> entityListToResponseList(
            List<CaixaEntity> entities
    ) {

        return entities.stream()
                .map(CaixaMapperRecord::entityToResponse)
                .toList();
    }
}
