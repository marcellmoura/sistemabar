package com.bar.sistemabar.internal.usuario.repository;

import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    boolean existsByEmail(String email);
}