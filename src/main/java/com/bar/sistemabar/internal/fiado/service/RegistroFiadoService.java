package com.bar.sistemabar.internal.fiado.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.caixa.repository.CaixaRepository;
import com.bar.sistemabar.internal.contagemEstoqueDia.entity.ContagemEstoqueDiaEntity;
import com.bar.sistemabar.internal.contagemEstoqueDia.repository.ContagemEstoqueDiaRepository;
import com.bar.sistemabar.internal.fiado.dto.RegistroFiadoRequestRecord;
import com.bar.sistemabar.internal.fiado.dto.RegistroFiadoResponseRecord;
import com.bar.sistemabar.internal.fiado.entity.PessoaFiadoEntity;
import com.bar.sistemabar.internal.fiado.entity.RegistroFiadoEntity;
import com.bar.sistemabar.internal.fiado.entity.StatusFiado;
import com.bar.sistemabar.internal.fiado.entity.StatusPessoaFiado;
import com.bar.sistemabar.internal.fiado.mapper.RegistroFiadoMapperRecord;
import com.bar.sistemabar.internal.fiado.repository.PessoaFiadoRepository;
import com.bar.sistemabar.internal.fiado.repository.RegistroFiadoRepository;
import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.movimentoDia.entity.StatusMovimentoDia;
import com.bar.sistemabar.internal.movimentoDia.repository.MovimentoDiaRepository;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.produto.repository.ProdutoRepository;
import com.bar.sistemabar.internal.saidaProduto.entity.SaidaProdutoEntity;
import com.bar.sistemabar.internal.saidaProduto.entity.TipoSaidaProduto;
import com.bar.sistemabar.internal.saidaProduto.repository.SaidaProdutoRepository;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import com.bar.sistemabar.internal.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroFiadoService {

    private final RegistroFiadoRepository registroFiadoRepository;
    private final PessoaFiadoRepository pessoaFiadoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MovimentoDiaRepository movimentoDiaRepository;
    private final SaidaProdutoRepository saidaProdutoRepository;
    private final ContagemEstoqueDiaRepository contagemEstoqueDiaRepository;
    private final CaixaRepository caixaRepository;

    public RegistroFiadoService(
            RegistroFiadoRepository registroFiadoRepository,
            PessoaFiadoRepository pessoaFiadoRepository,
            ProdutoRepository produtoRepository,
            UsuarioRepository usuarioRepository,
            MovimentoDiaRepository movimentoDiaRepository,
            SaidaProdutoRepository saidaProdutoRepository,
            ContagemEstoqueDiaRepository contagemEstoqueDiaRepository,
            CaixaRepository caixaRepository
    ) {
        this.registroFiadoRepository = registroFiadoRepository;
        this.pessoaFiadoRepository = pessoaFiadoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.movimentoDiaRepository = movimentoDiaRepository;
        this.saidaProdutoRepository = saidaProdutoRepository;
        this.contagemEstoqueDiaRepository = contagemEstoqueDiaRepository;
        this.caixaRepository = caixaRepository;
    }

    @Transactional
    public RegistroFiadoResponseRecord registrar(RegistroFiadoRequestRecord request) {

        MovimentoDiaEntity movimentoDia = movimentoDiaRepository
                .findByDataMovimentoAndStatus(
                        LocalDate.now(),
                        StatusMovimentoDia.ABERTO
                )
                .orElseThrow(() ->
                        new BusinessException(
                                "Não existe movimento do dia aberto para registrar fiado."
                        )
                );

        PessoaFiadoEntity pessoaFiado = pessoaFiadoRepository
                .findById(request.pessoaFiadoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Pessoa do fiado não encontrada."
                        )
                );

        if (pessoaFiado.getStatus() != StatusPessoaFiado.ATIVO) {
            throw new BusinessException(
                    "Não é possível registrar fiado para pessoa inativa."
            );
        }

        ProdutoEntity produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Produto não encontrado.")
                );

        if (!Boolean.TRUE.equals(produto.getControlaEstoque())) {
            throw new BusinessException(
                    "Fiado de produto com controle por estoque deve ser registrado para produto que controla estoque."
            );
        }

        validarProdutoSemContagemFinal(produto.getId(), movimentoDia.getId());

        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Usuário não encontrado.")
                );

        Double valor = request.quantidade() * produto.getPreco();

        SaidaProdutoEntity saidaFiado = new SaidaProdutoEntity(
                request.quantidade(),
                TipoSaidaProduto.FIADO,
                LocalDateTime.now(),
                "Fiado registrado para: " + pessoaFiado.getNome(),
                produto,
                usuario,
                movimentoDia
        );

        saidaProdutoRepository.save(saidaFiado);

        RegistroFiadoEntity registroFiado = new RegistroFiadoEntity(
                request.quantidade(),
                valor,
                LocalDateTime.now(),
                request.observacao(),
                StatusFiado.ABERTO,
                pessoaFiado,
                produto,
                usuario,
                movimentoDia
        );

        RegistroFiadoEntity registroSalvo =
                registroFiadoRepository.save(registroFiado);

        return RegistroFiadoMapperRecord.entityToResponse(registroSalvo);
    }

    @Transactional(readOnly = true)
    public List<RegistroFiadoResponseRecord> listar() {

        List<RegistroFiadoEntity> registros =
                registroFiadoRepository.findAll();

        return RegistroFiadoMapperRecord
                .entityListToResponseList(registros);
    }

    @Transactional(readOnly = true)
    public List<RegistroFiadoResponseRecord> listarPorPessoa(Long pessoaFiadoId) {

        List<RegistroFiadoEntity> registros =
                registroFiadoRepository.findByPessoaFiadoId(pessoaFiadoId);

        return RegistroFiadoMapperRecord
                .entityListToResponseList(registros);
    }

    @Transactional(readOnly = true)
    public List<RegistroFiadoResponseRecord> listarEmAberto() {

        List<RegistroFiadoEntity> registros =
                registroFiadoRepository.findByStatus(StatusFiado.ABERTO);

        return RegistroFiadoMapperRecord
                .entityListToResponseList(registros);
    }

    @Transactional
    public RegistroFiadoResponseRecord marcarComoPago(Long id) {

        RegistroFiadoEntity registro =
                registroFiadoRepository.findById(id)
                        .orElseThrow(() ->
                                new RecursoNaoEncontradoException(
                                        "Registro de fiado não encontrado."
                                )
                        );

        if (registro.getStatus() != StatusFiado.ABERTO) {
            throw new BusinessException(
                    "Apenas fiados em aberto podem ser marcados como pagos."
            );
        }

        validarMovimentoSemCaixaConferido(registro);

        registro.setStatus(StatusFiado.PAGO);

        RegistroFiadoEntity registroAtualizado =
                registroFiadoRepository.save(registro);

        return RegistroFiadoMapperRecord.entityToResponse(registroAtualizado);
    }

    private void validarMovimentoSemCaixaConferido(
            RegistroFiadoEntity registro
    ) {

        boolean possuiCaixaConferido = caixaRepository.existsByMovimentoDiaId(
                registro.getMovimentoDia().getId()
        );

        if (possuiCaixaConferido) {
            throw new BusinessException(
                    "Não é possível marcar fiado como pago após a conferência do caixa."
            );
        }
    }

    private void validarProdutoSemContagemFinal(Long produtoId, Long movimentoDiaId) {

        boolean possuiContagemFinal = contagemEstoqueDiaRepository
                .findByProdutoIdAndMovimentoDiaId(produtoId, movimentoDiaId)
                .map(ContagemEstoqueDiaEntity::getQuantidadeFinal)
                .isPresent();

        if (possuiContagemFinal) {
            throw new BusinessException(
                    "Não é possível lançar movimentações para produto com contagem final já registrada."
            );
        }
    }
}