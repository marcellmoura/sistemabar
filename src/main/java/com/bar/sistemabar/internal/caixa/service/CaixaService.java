package com.bar.sistemabar.internal.caixa.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.caixa.dto.CaixaRequestRecord;
import com.bar.sistemabar.internal.caixa.dto.CaixaResponseRecord;
import com.bar.sistemabar.internal.caixa.entity.CaixaEntity;
import com.bar.sistemabar.internal.caixa.entity.StatusCaixa;
import com.bar.sistemabar.internal.caixa.mapper.CaixaMapperRecord;
import com.bar.sistemabar.internal.caixa.repository.CaixaRepository;
import com.bar.sistemabar.internal.contagemEstoqueDia.entity.ContagemEstoqueDiaEntity;
import com.bar.sistemabar.internal.contagemEstoqueDia.repository.ContagemEstoqueDiaRepository;
import com.bar.sistemabar.internal.fiado.entity.RegistroFiadoEntity;
import com.bar.sistemabar.internal.fiado.entity.StatusFiado;
import com.bar.sistemabar.internal.fiado.repository.RegistroFiadoRepository;
import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.movimentoDia.entity.StatusMovimentoDia;
import com.bar.sistemabar.internal.movimentoDia.repository.MovimentoDiaRepository;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import com.bar.sistemabar.internal.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CaixaService {

    private final CaixaRepository caixaRepository;
    private final MovimentoDiaRepository movimentoDiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContagemEstoqueDiaRepository contagemEstoqueDiaRepository;
    private final RegistroFiadoRepository registroFiadoRepository;

    public CaixaService(
            CaixaRepository caixaRepository,
            MovimentoDiaRepository movimentoDiaRepository,
            UsuarioRepository usuarioRepository,
            ContagemEstoqueDiaRepository contagemEstoqueDiaRepository,
            RegistroFiadoRepository registroFiadoRepository
    ) {
        this.caixaRepository = caixaRepository;
        this.movimentoDiaRepository = movimentoDiaRepository;
        this.usuarioRepository = usuarioRepository;
        this.contagemEstoqueDiaRepository = contagemEstoqueDiaRepository;
        this.registroFiadoRepository = registroFiadoRepository;
    }

    @Transactional
    public CaixaResponseRecord registrarConferencia(CaixaRequestRecord request) {

        MovimentoDiaEntity movimentoDia = movimentoDiaRepository
                .findById(request.movimentoDiaId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Movimento do dia não encontrado.")
                );

        if (movimentoDia.getStatus() != StatusMovimentoDia.FECHADO) {
            throw new BusinessException(
                    "Só é possível conferir caixa de movimento fechado."
            );
        }

        if (caixaRepository.existsByMovimentoDiaId(movimentoDia.getId())) {
            throw new BusinessException(
                    "Já existe conferência de caixa registrada para este movimento."
            );
        }

        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Usuário não encontrado.")
                );

        List<ContagemEstoqueDiaEntity> contagens =
                contagemEstoqueDiaRepository.findByMovimentoDiaId(movimentoDia.getId());

        Double valorVendidoCalculado = contagens.stream()
                .map(ContagemEstoqueDiaEntity::getValorVendidoCalculado)
                .reduce(0.0, Double::sum);

        List<RegistroFiadoEntity> fiados =
                registroFiadoRepository.findByMovimentoDiaId(movimentoDia.getId());

        Double valorFiadoAberto = fiados.stream()
                .filter(fiado -> fiado.getStatus() == StatusFiado.ABERTO)
                .map(RegistroFiadoEntity::getValor)
                .reduce(0.0, Double::sum);

        Double valorFiadoPago = fiados.stream()
                .filter(fiado -> fiado.getStatus() == StatusFiado.PAGO)
                .map(RegistroFiadoEntity::getValor)
                .reduce(0.0, Double::sum);

        Double valorTotalFiado = valorFiadoAberto + valorFiadoPago;

        Double valorRecebidoEstimado = valorVendidoCalculado + valorFiadoPago;

        Double diferenca = request.valorInformado() - valorRecebidoEstimado;

        CaixaEntity caixa = new CaixaEntity();

        caixa.setMovimentoDia(movimentoDia);
        caixa.setUsuario(usuario);
        caixa.setValorVendidoCalculado(valorVendidoCalculado);
        caixa.setValorFiadoAberto(valorFiadoAberto);
        caixa.setValorFiadoPago(valorFiadoPago);
        caixa.setValorTotalFiado(valorTotalFiado);
        caixa.setValorRecebidoEstimado(valorRecebidoEstimado);
        caixa.setValorInformado(request.valorInformado());
        caixa.setDiferenca(diferenca);
        caixa.setDataHoraConferencia(LocalDateTime.now());
        caixa.setObservacao(request.observacao());
        caixa.setStatus(StatusCaixa.CONFERIDO);

        CaixaEntity caixaSalvo = caixaRepository.save(caixa);

        return CaixaMapperRecord.entityToResponse(caixaSalvo);
    }

    @Transactional(readOnly = true)
    public List<CaixaResponseRecord> listar() {

        List<CaixaEntity> caixas = caixaRepository.findAll();

        return CaixaMapperRecord.entityListToResponseList(caixas);
    }

    @Transactional(readOnly = true)
    public CaixaResponseRecord buscarPorMovimento(Long movimentoDiaId) {

        CaixaEntity caixa = caixaRepository.findByMovimentoDiaId(movimentoDiaId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Conferência de caixa não encontrada para este movimento."
                        )
                );

        return CaixaMapperRecord.entityToResponse(caixa);
    }
}
