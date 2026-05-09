package com.bar.sistemabar.internal.entradaProduto.dto;

import java.time.LocalDateTime;

public record EntradaProdutoResponseRecord(
        Long id,
        Long produtoId,
        String produtoNome,
        Long usuarioId,
        String usuarioNome,
        Integer quantidade,
        String tipoEntrada,
        LocalDateTime dataHora
) {
}