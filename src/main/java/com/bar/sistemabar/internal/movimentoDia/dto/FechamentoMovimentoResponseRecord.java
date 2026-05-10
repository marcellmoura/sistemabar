package com.bar.sistemabar.internal.movimentoDia.dto;

import com.bar.sistemabar.internal.movimentoDia.entity.StatusMovimentoDia;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FechamentoMovimentoResponseRecord(

        Long id,

        LocalDate dataMovimento,

        LocalDateTime dataHoraAbertura,

        LocalDateTime dataHoraFechamento,

        StatusMovimentoDia status

) {
}