package com.bar.sistemabar.internal.entradaProduto.dto;

import com.bar.sistemabar.internal.entradaProduto.entity.TipoEntradaProduto;

import java.time.LocalDateTime;

public record EntradaProdutoResponseRecord(

        Long id,
        Long produtoId,
        String produtoNome,
        Long usuarioId,
        String usuarioNome,
        Integer quantidade,
        TipoEntradaProduto tipoEntrada,
        LocalDateTime dataHora

) {
}