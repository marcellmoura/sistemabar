package com.bar.sistemabar.internal.usuario.dto;

public record UsuarioResponseRecord(
        Long id,
        String nome,
        String email,
        String perfil,
        String status
) {}
