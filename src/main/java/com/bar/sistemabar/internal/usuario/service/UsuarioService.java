package com.bar.sistemabar.internal.usuario.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.usuario.dto.UsuarioRequestRecord;
import com.bar.sistemabar.internal.usuario.dto.UsuarioResponseRecord;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import com.bar.sistemabar.internal.usuario.mapper.UsuarioMapperRecord;
import com.bar.sistemabar.internal.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapperRecord usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapperRecord usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponseRecord cadastrar(UsuarioRequestRecord request) {

        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("Já existe usuário com esse email.");
        }

        UsuarioEntity entity = usuarioMapper.toEntity(request);

        return usuarioMapper.toResponse(usuarioRepository.save(entity));
    }

    public UsuarioResponseRecord editar(Long id, UsuarioRequestRecord request) {
        UsuarioEntity entity = buscarPorIdEntity(id);

        entity.setNome(request.nome());
        entity.setEmail(request.email());
        entity.setSenha(request.senha());
        entity.setPerfil(request.perfil());
        entity.setStatus(request.status());

        return usuarioMapper.toResponse(usuarioRepository.save(entity));
    }

    public UsuarioResponseRecord ativar(Long id) {
        UsuarioEntity entity = buscarPorIdEntity(id);

        entity.setStatus("ATIVO");

        return usuarioMapper.toResponse(usuarioRepository.save(entity));
    }

    public UsuarioResponseRecord inativar(Long id) {
        UsuarioEntity entity = buscarPorIdEntity(id);

        entity.setStatus("INATIVO");

        return usuarioMapper.toResponse(usuarioRepository.save(entity));
    }

    public UsuarioResponseRecord buscarPorId(Long id) {
        return usuarioMapper.toResponse(buscarPorIdEntity(id));
    }

    public List<UsuarioResponseRecord> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    private UsuarioEntity buscarPorIdEntity(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com id: " + id));
    }
}