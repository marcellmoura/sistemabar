package com.bar.sistemabar.internal.caixa.dto;

import com.bar.sistemabar.internal.caixa.entity.StatusCaixa;

import java.time.LocalDateTime;

public record CaixaResponseRecord(

        Long id,

        Long movimentoDiaId,

        Long usuarioId,

        String usuarioNome,

        Double valorVendidoCalculado,

        Double valorFiadoAberto,

        Double valorFiadoPago,

        Double valorTotalFiado,

        Double valorRecebidoEstimado,

        Double valorInformado,

        Double diferenca,

        LocalDateTime dataHoraConferencia,

        String observacao,

        StatusCaixa status

) {
}
