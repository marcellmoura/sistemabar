package com.bar.sistemabar.internal.movimentoDia.mapper;

import com.bar.sistemabar.internal.movimentoDia.dto.MovimentoDiaResponseRecord;
import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;

public class MovimentoDiaMapperRecord {

    public static MovimentoDiaResponseRecord entityToResponseRecord(MovimentoDiaEntity entity) {

        return new MovimentoDiaResponseRecord(
                entity.getId(),
                entity.getDataMovimento(),
                entity.getDataHoraAbertura(),
                entity.getDataHoraFechamento(),
                entity.getTrocoInicial(),
                entity.getStatus(),
                entity.getUsuarioResponsavel().getNome()
        );
    }
}