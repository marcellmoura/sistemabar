package com.bar.sistemabar.internal.movimentoDia.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.caixa.dto.CaixaResponseRecord;
import com.bar.sistemabar.internal.caixa.entity.CaixaEntity;
import com.bar.sistemabar.internal.caixa.mapper.CaixaMapperRecord;
import com.bar.sistemabar.internal.caixa.repository.CaixaRepository;
import com.bar.sistemabar.internal.contagemEstoqueDia.dto.ContagemEstoqueDiaResponseRecord;
import com.bar.sistemabar.internal.contagemEstoqueDia.entity.ContagemEstoqueDiaEntity;
import com.bar.sistemabar.internal.contagemEstoqueDia.mapper.ContagemEstoqueDiaMapperRecord;
import com.bar.sistemabar.internal.contagemEstoqueDia.repository.ContagemEstoqueDiaRepository;
import com.bar.sistemabar.internal.fiado.dto.RegistroFiadoResponseRecord;
import com.bar.sistemabar.internal.fiado.entity.RegistroFiadoEntity;
import com.bar.sistemabar.internal.fiado.entity.StatusFiado;
import com.bar.sistemabar.internal.fiado.mapper.RegistroFiadoMapperRecord;
import com.bar.sistemabar.internal.fiado.repository.RegistroFiadoRepository;
import com.bar.sistemabar.internal.movimentoDia.dto.FechamentoMovimentoResponseRecord;
import com.bar.sistemabar.internal.movimentoDia.dto.MovimentoDiaRequestRecord;
import com.bar.sistemabar.internal.movimentoDia.dto.MovimentoDiaResponseRecord;
import com.bar.sistemabar.internal.movimentoDia.dto.ResumoMovimentoResponseRecord;
import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.movimentoDia.entity.StatusMovimentoDia;
import com.bar.sistemabar.internal.movimentoDia.mapper.MovimentoDiaMapperRecord;
import com.bar.sistemabar.internal.movimentoDia.repository.MovimentoDiaRepository;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.produto.entity.StatusProduto;
import com.bar.sistemabar.internal.produto.repository.ProdutoRepository;
import com.bar.sistemabar.internal.saidaProduto.dto.SaidaProdutoResponseRecord;
import com.bar.sistemabar.internal.saidaProduto.entity.SaidaProdutoEntity;
import com.bar.sistemabar.internal.saidaProduto.mapper.SaidaProdutoMapperRecord;
import com.bar.sistemabar.internal.saidaProduto.repository.SaidaProdutoRepository;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import com.bar.sistemabar.internal.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentoDiaService {

    private final MovimentoDiaRepository movimentoDiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContagemEstoqueDiaRepository contagemEstoqueDiaRepository;
    private final ProdutoRepository produtoRepository;
    private final RegistroFiadoRepository registroFiadoRepository;
    private final SaidaProdutoRepository saidaProdutoRepository;
    private final CaixaRepository caixaRepository;

    public MovimentoDiaService(
            MovimentoDiaRepository movimentoDiaRepository,
            UsuarioRepository usuarioRepository,
            ContagemEstoqueDiaRepository contagemEstoqueDiaRepository,
            ProdutoRepository produtoRepository,
            RegistroFiadoRepository registroFiadoRepository,
            SaidaProdutoRepository saidaProdutoRepository,
            CaixaRepository caixaRepository
    ) {

        this.movimentoDiaRepository = movimentoDiaRepository;
        this.usuarioRepository = usuarioRepository;
        this.contagemEstoqueDiaRepository = contagemEstoqueDiaRepository;
        this.produtoRepository = produtoRepository;
        this.registroFiadoRepository = registroFiadoRepository;
        this.saidaProdutoRepository = saidaProdutoRepository;
        this.caixaRepository = caixaRepository;
    }

    @Transactional
    public MovimentoDiaResponseRecord abrirMovimento(MovimentoDiaRequestRecord request) {

        if (movimentoDiaRepository
                .findByDataMovimentoAndStatus(
                        LocalDate.now(),
                        StatusMovimentoDia.ABERTO
                )
                .isPresent()) {

            throw new BusinessException(
                    "Já existe um movimento aberto para hoje"
            );
        }

        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioResponsavelId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Usuário não encontrado"));

        MovimentoDiaEntity movimentoDia = new MovimentoDiaEntity();

        movimentoDia.setDataMovimento(LocalDate.now());
        movimentoDia.setDataHoraAbertura(LocalDateTime.now());
        movimentoDia.setTrocoInicial(request.trocoInicial());
        movimentoDia.setStatus(StatusMovimentoDia.ABERTO);
        movimentoDia.setUsuarioResponsavel(usuario);

        MovimentoDiaEntity movimentoSalvo =
                movimentoDiaRepository.save(movimentoDia);

        return MovimentoDiaMapperRecord
                .entityToResponseRecord(movimentoSalvo);
    }

    @Transactional
    public FechamentoMovimentoResponseRecord fecharMovimento(Long id) {

        MovimentoDiaEntity movimentoDia = movimentoDiaRepository
                .findByIdAndStatus(id, StatusMovimentoDia.ABERTO)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Movimento aberto não encontrado"
                        ));

        List<ContagemEstoqueDiaEntity> contagens =
                contagemEstoqueDiaRepository.findByMovimentoDiaId(id);

        if (contagens.isEmpty()) {
            throw new BusinessException(
                    "Não é possível fechar o movimento sem contagens de estoque registradas"
            );
        }

        validarContagensComFinal(contagens);

        validarTodosProdutosComEstoqueForamContados(contagens);

        movimentoDia.setStatus(StatusMovimentoDia.FECHADO);
        movimentoDia.setDataHoraFechamento(LocalDateTime.now());

        MovimentoDiaEntity movimentoFechado =
                movimentoDiaRepository.save(movimentoDia);

        return montarRespostaFechamento(movimentoFechado, contagens);
    }

    @Transactional
    public ResumoMovimentoResponseRecord buscarResumo(Long id) {

        MovimentoDiaEntity movimentoDia = movimentoDiaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Movimento do dia não encontrado")
                );

        List<ContagemEstoqueDiaEntity> contagens =
                contagemEstoqueDiaRepository.findByMovimentoDiaId(id);

        List<SaidaProdutoEntity> saidas =
                saidaProdutoRepository.findByMovimentoDiaId(id);

        List<RegistroFiadoEntity> fiados =
                registroFiadoRepository.findByMovimentoDiaId(id);

        List<ContagemEstoqueDiaResponseRecord> contagensResponse =
                ContagemEstoqueDiaMapperRecord.entityListToResponseList(contagens);

        List<SaidaProdutoResponseRecord> saidasResponse =
                SaidaProdutoMapperRecord.entityListToResponseList(saidas);

        List<RegistroFiadoResponseRecord> fiadosResponse =
                RegistroFiadoMapperRecord.entityListToResponseList(fiados);

        CaixaResponseRecord caixaResponse = caixaRepository.findByMovimentoDiaId(id)
                .map(CaixaMapperRecord::entityToResponse)
                .orElse(null);

        Double valorVendidoCalculado = calcularValorVendidoCalculado(contagens);

        Double valorFiadoAberto = calcularValorFiadoPorStatus(
                fiados,
                StatusFiado.ABERTO
        );

        Double valorFiadoPago = calcularValorFiadoPorStatus(
                fiados,
                StatusFiado.PAGO
        );

        Double valorTotalFiado = valorFiadoAberto + valorFiadoPago;

        Double valorRecebidoEstimado = valorVendidoCalculado + valorFiadoPago;

        return new ResumoMovimentoResponseRecord(
                movimentoDia.getId(),
                movimentoDia.getDataMovimento(),
                movimentoDia.getDataHoraAbertura(),
                movimentoDia.getDataHoraFechamento(),
                movimentoDia.getTrocoInicial(),
                movimentoDia.getStatus(),
                movimentoDia.getUsuarioResponsavel().getNome(),
                valorVendidoCalculado,
                valorFiadoAberto,
                valorFiadoPago,
                valorTotalFiado,
                valorRecebidoEstimado,
                contagensResponse,
                saidasResponse,
                fiadosResponse,
                caixaResponse
        );
    }

    private void validarContagensComFinal(
            List<ContagemEstoqueDiaEntity> contagens
    ) {

        List<String> produtosSemContagemFinal = contagens.stream()
                .filter(contagem -> contagem.getQuantidadeFinal() == null)
                .map(contagem -> contagem.getProduto().getNome())
                .toList();

        if (!produtosSemContagemFinal.isEmpty()) {
            throw new BusinessException(
                    "Não é possível fechar o movimento. Existem produtos sem contagem final: "
                            + String.join(", ", produtosSemContagemFinal)
            );
        }
    }

    private void validarTodosProdutosComEstoqueForamContados(
            List<ContagemEstoqueDiaEntity> contagens
    ) {

        List<ProdutoEntity> produtosComEstoque =
                produtoRepository.findByControlaEstoqueTrueAndStatus(
                        StatusProduto.ATIVO
                );

        List<Long> produtosContadosIds = contagens.stream()
                .map(contagem -> contagem.getProduto().getId())
                .toList();

        List<String> produtosSemContagem = produtosComEstoque.stream()
                .filter(produto ->
                        !produtosContadosIds.contains(produto.getId())
                )
                .map(ProdutoEntity::getNome)
                .toList();

        if (!produtosSemContagem.isEmpty()) {
            throw new BusinessException(
                    "Não é possível fechar o movimento. Existem produtos com controle de estoque sem contagem registrada: "
                            + String.join(", ", produtosSemContagem)
            );
        }
    }

    private FechamentoMovimentoResponseRecord montarRespostaFechamento(
            MovimentoDiaEntity movimentoDia,
            List<ContagemEstoqueDiaEntity> contagens
    ) {

        Double valorVendidoCalculado = calcularValorVendidoCalculado(contagens);

        List<RegistroFiadoEntity> fiados =
                registroFiadoRepository.findByMovimentoDiaId(movimentoDia.getId());

        Double valorFiadoAberto = calcularValorFiadoPorStatus(
                fiados,
                StatusFiado.ABERTO
        );

        Double valorFiadoPago = calcularValorFiadoPorStatus(
                fiados,
                StatusFiado.PAGO
        );

        Double valorTotalFiado = valorFiadoAberto + valorFiadoPago;

        Double valorRecebidoEstimado = valorVendidoCalculado + valorFiadoPago;

        return new FechamentoMovimentoResponseRecord(
                movimentoDia.getId(),
                movimentoDia.getDataMovimento(),
                movimentoDia.getDataHoraAbertura(),
                movimentoDia.getDataHoraFechamento(),
                movimentoDia.getTrocoInicial(),
                movimentoDia.getStatus(),
                movimentoDia.getUsuarioResponsavel().getNome(),
                valorVendidoCalculado,
                valorFiadoAberto,
                valorFiadoPago,
                valorTotalFiado,
                valorRecebidoEstimado
        );
    }

    private Double calcularValorVendidoCalculado(
            List<ContagemEstoqueDiaEntity> contagens
    ) {

        return contagens.stream()
                .map(ContagemEstoqueDiaEntity::getValorVendidoCalculado)
                .filter(valor -> valor != null)
                .reduce(0.0, Double::sum);
    }

    private Double calcularValorFiadoPorStatus(
            List<RegistroFiadoEntity> fiados,
            StatusFiado status
    ) {

        return fiados.stream()
                .filter(fiado -> fiado.getStatus() == status)
                .map(RegistroFiadoEntity::getValor)
                .filter(valor -> valor != null)
                .reduce(0.0, Double::sum);
    }
}
