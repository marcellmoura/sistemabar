package com.bar.sistemabar.internal.contagemEstoqueDia.dto;

public record ContagemEstoqueDiaResponseRecord(

        Long id,
        Long produtoId,
        String produtoNome,
        Long movimentoDiaId,
        Integer quantidadeInicial,
        Integer quantidadeFinal,
        Integer quantidadeVendidaCalculada,
        Double valorVendidoCalculado

) {
}