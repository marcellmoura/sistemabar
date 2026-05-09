package com.bar.sistemabar.internal.contagemEstoqueDia.mapper;

import com.bar.sistemabar.internal.contagemEstoqueDia.dto.ContagemEstoqueDiaResponseRecord;
import com.bar.sistemabar.internal.contagemEstoqueDia.entity.ContagemEstoqueDiaEntity;

import java.util.List;

public record ContagemEstoqueDiaMapperRecord() {

    public static ContagemEstoqueDiaResponseRecord entityToResponse(
            ContagemEstoqueDiaEntity entity
    ) {

        return new ContagemEstoqueDiaResponseRecord(
                entity.getId(),
                entity.getProduto().getId(),
                entity.getProduto().getNome(),
                entity.getMovimentoDia().getId(),
                entity.getQuantidadeInicial(),
                entity.getQuantidadeFinal(),
                entity.getQuantidadeVendidaCalculada(),
                entity.getValorVendidoCalculado()
        );
    }

    public static List<ContagemEstoqueDiaResponseRecord> entityListToResponseList(
            List<ContagemEstoqueDiaEntity> entities
    ) {

        return entities.stream()
                .map(ContagemEstoqueDiaMapperRecord::entityToResponse)
                .toList();
    }
}