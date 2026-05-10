package com.bar.sistemabar.internal.fiado.dto;

import com.bar.sistemabar.internal.fiado.entity.StatusFiado;

import java.time.LocalDateTime;

public record RegistroFiadoResponseRecord(

        Long id,
        Long pessoaFiadoId,
        String pessoaFiadoNome,
        Long produtoId,
        String produtoNome,
        Long usuarioId,
        String usuarioNome,
        Long movimentoDiaId,
        Integer quantidade,
        Double valor,
        LocalDateTime dataHora,
        String observacao,
        StatusFiado status

) {
}
