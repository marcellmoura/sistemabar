package com.bar.sistemabar.internal.categoria.service;

import com.bar.sistemabar.internal.categoria.dto.CategoriaRequestRecord;
import com.bar.sistemabar.internal.categoria.dto.CategoriaResponseRecord;
import com.bar.sistemabar.internal.categoria.entity.CategoriaEntity;
import com.bar.sistemabar.internal.categoria.mapper.CategoriaMapperRecord;
import com.bar.sistemabar.internal.categoria.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponseRecord registrar(CategoriaRequestRecord request) {

        CategoriaEntity categoriaEntity = CategoriaMapperRecord.toEntity(request);

        CategoriaEntity categoriaSalva = categoriaRepository.save(categoriaEntity);

        return CategoriaMapperRecord.toResponse(categoriaSalva);
    }

    public List<CategoriaResponseRecord> listar() {

        List<CategoriaEntity> categorias = categoriaRepository.findAll();

        return CategoriaMapperRecord.toResponseList(categorias);
    }
}