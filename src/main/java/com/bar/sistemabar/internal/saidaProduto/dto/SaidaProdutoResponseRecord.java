package com.bar.sistemabar.internal.saidaProduto.dto;

import com.bar.sistemabar.internal.saidaProduto.entity.TipoSaidaProduto;

import java.time.LocalDateTime;

public record SaidaProdutoResponseRecord(

        Long id,
        Long produtoId,
        String produtoNome,
        Long usuarioId,
        String usuarioNome,
        Integer quantidade,
        TipoSaidaProduto tipoSaida,
        LocalDateTime dataHora,
        String observacao

) {
}
