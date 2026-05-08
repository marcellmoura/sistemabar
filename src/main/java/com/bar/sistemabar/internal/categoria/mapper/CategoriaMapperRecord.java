package com.bar.sistemabar.internal.categoria.mapper;

import com.bar.sistemabar.internal.categoria.dto.CategoriaRequestRecord;
import com.bar.sistemabar.internal.categoria.dto.CategoriaResponseRecord;
import com.bar.sistemabar.internal.categoria.entity.CategoriaEntity;

import java.util.List;

public record CategoriaMapperRecord() {

    public static CategoriaEntity toEntity(CategoriaRequestRecord request) {

        CategoriaEntity categoriaEntity = new CategoriaEntity();

        categoriaEntity.setNome(request.nome());
        categoriaEntity.setStatus(request.status());

        return categoriaEntity;
    }

    public static CategoriaResponseRecord toResponse(CategoriaEntity entity) {

        return new CategoriaResponseRecord(
                entity.getId(),
                entity.getNome(),
                entity.getStatus()
        );
    }

    public static List<CategoriaResponseRecord> toResponseList(List<CategoriaEntity> entities) {

        return entities.stream()
                .map(CategoriaMapperRecord::toResponse)
                .toList();
    }
}