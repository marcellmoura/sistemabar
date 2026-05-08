package com.bar.sistemabar.internal.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestRecord(

        @NotBlank(message = "O nome do usuário é obrigatório")
        String nome,

        @NotBlank(message = "O e-mail do usuário é obrigatório")
        @Email(message = "Informe um e-mail válido")
        String email,

        @NotBlank(message = "A senha do usuário é obrigatória")
        String senha,

        @NotBlank(message = "O perfil do usuário é obrigatório")
        String perfil,

        @NotBlank(message = "O status do usuário é obrigatório")
        String status
) {}