package com.bar.sistemabar.internal.saidaProduto.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.contagemEstoqueDia.entity.ContagemEstoqueDiaEntity;
import com.bar.sistemabar.internal.contagemEstoqueDia.repository.ContagemEstoqueDiaRepository;
import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.movimentoDia.entity.StatusMovimentoDia;
import com.bar.sistemabar.internal.movimentoDia.repository.MovimentoDiaRepository;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.produto.repository.ProdutoRepository;
import com.bar.sistemabar.internal.saidaProduto.dto.SaidaProdutoRequestRecord;
import com.bar.sistemabar.internal.saidaProduto.dto.SaidaProdutoResponseRecord;
import com.bar.sistemabar.internal.saidaProduto.entity.SaidaProdutoEntity;
import com.bar.sistemabar.internal.saidaProduto.entity.TipoSaidaProduto;
import com.bar.sistemabar.internal.saidaProduto.mapper.SaidaProdutoMapperRecord;
import com.bar.sistemabar.internal.saidaProduto.repository.SaidaProdutoRepository;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import com.bar.sistemabar.internal.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaidaProdutoService {

    private final SaidaProdutoRepository saidaProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MovimentoDiaRepository movimentoDiaRepository;
    private final ContagemEstoqueDiaRepository contagemEstoqueDiaRepository;

    public SaidaProdutoService(
            SaidaProdutoRepository saidaProdutoRepository,
            ProdutoRepository produtoRepository,
            UsuarioRepository usuarioRepository,
            MovimentoDiaRepository movimentoDiaRepository,
            ContagemEstoqueDiaRepository contagemEstoqueDiaRepository
    ) {
        this.saidaProdutoRepository = saidaProdutoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.movimentoDiaRepository = movimentoDiaRepository;
        this.contagemEstoqueDiaRepository = contagemEstoqueDiaRepository;
    }

    @Transactional
    public SaidaProdutoResponseRecord registrar(SaidaProdutoRequestRecord request) {

        MovimentoDiaEntity movimentoDia = movimentoDiaRepository
                .findByDataMovimentoAndStatus(
                        LocalDate.now(),
                        StatusMovimentoDia.ABERTO
                )
                .orElseThrow(() ->
                        new BusinessException(
                                "Não existe movimento do dia aberto para registrar saída de produto."
                        )
                );

        ProdutoEntity produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Produto não encontrado.")
                );

        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Usuário não encontrado.")
                );

        if (!Boolean.TRUE.equals(produto.getControlaEstoque())) {
            throw new BusinessException(
                    "Saídas especiais só podem ser registradas para produtos com controle de estoque."
            );
        }

        validarProdutoSemContagemFinal(produto.getId(), movimentoDia.getId());

        validarObservacaoObrigatoria(request);

        SaidaProdutoEntity saidaProduto = new SaidaProdutoEntity(
                request.quantidade(),
                request.tipoSaida(),
                LocalDateTime.now(),
                request.observacao(),
                produto,
                usuario,
                movimentoDia
        );

        SaidaProdutoEntity saidaSalva =
                saidaProdutoRepository.save(saidaProduto);

        return SaidaProdutoMapperRecord
                .entityToResponseRecord(saidaSalva);
    }

    @Transactional(readOnly = true)
    public List<SaidaProdutoResponseRecord> listar() {

        List<SaidaProdutoEntity> saidas =
                saidaProdutoRepository.findAll();

        return SaidaProdutoMapperRecord
                .entityListToResponseList(saidas);
    }

    @Transactional(readOnly = true)
    public List<SaidaProdutoResponseRecord> listarPorMovimento(Long movimentoDiaId) {

        List<SaidaProdutoEntity> saidas =
                saidaProdutoRepository.findByMovimentoDiaId(movimentoDiaId);

        return SaidaProdutoMapperRecord
                .entityListToResponseList(saidas);
    }

    private void validarObservacaoObrigatoria(SaidaProdutoRequestRecord request) {

        boolean exigeObservacao =
                request.tipoSaida() == TipoSaidaProduto.PERDA
                        || request.tipoSaida() == TipoSaidaProduto.QUEBRA
                        || request.tipoSaida() == TipoSaidaProduto.OUTRA;

        boolean observacaoVazia =
                request.observacao() == null
                        || request.observacao().isBlank();

        if (exigeObservacao && observacaoVazia) {
            throw new BusinessException(
                    "A observação é obrigatória para saídas do tipo PERDA, QUEBRA ou OUTRA."
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
