package com.bar.sistemabar.internal.fiado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PessoaFiadoRequestRecord(

        @NotBlank(message = "O nome da pessoa é obrigatório.")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
        String nome,

        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
        String telefone

) {
}
