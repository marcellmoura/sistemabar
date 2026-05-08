package com.bar.sistemabar.internal.usuario.mapper;

import com.bar.sistemabar.internal.usuario.dto.UsuarioRequestRecord;
import com.bar.sistemabar.internal.usuario.dto.UsuarioResponseRecord;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapperRecord {

    public UsuarioEntity toEntity(UsuarioRequestRecord request) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNome(request.nome());
        entity.setEmail(request.email());
        entity.setSenha(request.senha());
        entity.setPerfil(request.perfil());
        entity.setStatus(request.status());
        return entity;
    }

    public UsuarioResponseRecord toResponse(UsuarioEntity entity) {
        return new UsuarioResponseRecord(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getPerfil(),
                entity.getStatus()
        );
    }
}