package com.bar.sistemabar.internal.movimentoDia.dto;

import com.bar.sistemabar.internal.movimentoDia.entity.StatusMovimentoDia;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record FechamentoMovimentoResponseRecord(

        Long id,

        LocalDate dataMovimento,

        LocalDateTime dataHoraAbertura,

        LocalDateTime dataHoraFechamento,

        BigDecimal trocoInicial,

        StatusMovimentoDia status,

        String usuarioResponsavel,

        Double valorVendidoCalculado,

        Double valorFiadoAberto,

        Double valorFiadoPago,

        Double valorTotalFiado,

        Double valorRecebidoEstimado

) {
}
