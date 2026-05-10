package com.bar.sistemabar.internal.fiado.mapper;

import com.bar.sistemabar.internal.fiado.dto.PessoaFiadoRequestRecord;
import com.bar.sistemabar.internal.fiado.dto.PessoaFiadoResponseRecord;
import com.bar.sistemabar.internal.fiado.entity.PessoaFiadoEntity;
import com.bar.sistemabar.internal.fiado.entity.StatusPessoaFiado;

import java.util.List;

public record PessoaFiadoMapperRecord() {

    public static PessoaFiadoEntity requestToEntity(PessoaFiadoRequestRecord request) {

        return new PessoaFiadoEntity(
                request.nome(),
                request.telefone(),
                StatusPessoaFiado.ATIVO
        );
    }

    public static PessoaFiadoResponseRecord entityToResponse(PessoaFiadoEntity entity) {

        return new PessoaFiadoResponseRecord(
                entity.getId(),
                entity.getNome(),
                entity.getTelefone(),
                entity.getStatus()
        );
    }

    public static List<PessoaFiadoResponseRecord> entityListToResponseList(
            List<PessoaFiadoEntity> entities
    ) {

        return entities.stream()
                .map(PessoaFiadoMapperRecord::entityToResponse)
                .toList();
    }
}
