package com.bar.sistemabar.internal.movimentoDia.dto;

import com.bar.sistemabar.internal.caixa.dto.CaixaResponseRecord;
import com.bar.sistemabar.internal.contagemEstoqueDia.dto.ContagemEstoqueDiaResponseRecord;
import com.bar.sistemabar.internal.fiado.dto.RegistroFiadoResponseRecord;
import com.bar.sistemabar.internal.movimentoDia.entity.StatusMovimentoDia;
import com.bar.sistemabar.internal.saidaProduto.dto.SaidaProdutoResponseRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ResumoMovimentoResponseRecord(

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

        Double valorRecebidoEstimado,

        List<ContagemEstoqueDiaResponseRecord> contagens,

        List<SaidaProdutoResponseRecord> saidasEspeciais,

        List<RegistroFiadoResponseRecord> fiados,

        CaixaResponseRecord caixa

) {
}
