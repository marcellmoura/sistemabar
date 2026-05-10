package com.bar.sistemabar.internal.fiado.dto;

import com.bar.sistemabar.internal.fiado.entity.StatusPessoaFiado;

public record PessoaFiadoResponseRecord(

        Long id,
        String nome,
        String telefone,
        StatusPessoaFiado status

) {
}
