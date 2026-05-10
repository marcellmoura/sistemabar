package com.bar.sistemabar.internal.entradaProduto.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.contagemEstoqueDia.entity.ContagemEstoqueDiaEntity;
import com.bar.sistemabar.internal.contagemEstoqueDia.repository.ContagemEstoqueDiaRepository;
import com.bar.sistemabar.internal.entradaProduto.dto.EntradaProdutoRequestRecord;
import com.bar.sistemabar.internal.entradaProduto.dto.EntradaProdutoResponseRecord;
import com.bar.sistemabar.internal.entradaProduto.entity.EntradaProdutoEntity;
import com.bar.sistemabar.internal.entradaProduto.mapper.EntradaProdutoMapperRecord;
import com.bar.sistemabar.internal.entradaProduto.repository.EntradaProdutoRepository;
import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.movimentoDia.entity.StatusMovimentoDia;
import com.bar.sistemabar.internal.movimentoDia.repository.MovimentoDiaRepository;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.produto.repository.ProdutoRepository;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import com.bar.sistemabar.internal.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EntradaProdutoService {

    private final EntradaProdutoRepository entradaProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MovimentoDiaRepository movimentoDiaRepository;
    private final ContagemEstoqueDiaRepository contagemEstoqueDiaRepository;

    public EntradaProdutoService(
            EntradaProdutoRepository entradaProdutoRepository,
            ProdutoRepository produtoRepository,
            UsuarioRepository usuarioRepository,
            MovimentoDiaRepository movimentoDiaRepository,
            ContagemEstoqueDiaRepository contagemEstoqueDiaRepository
    ) {
        this.entradaProdutoRepository = entradaProdutoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.movimentoDiaRepository = movimentoDiaRepository;
        this.contagemEstoqueDiaRepository = contagemEstoqueDiaRepository;
    }

    @Transactional
    public EntradaProdutoResponseRecord cadastrar(EntradaProdutoRequestRecord request) {

        MovimentoDiaEntity movimentoDia = movimentoDiaRepository
                .findByDataMovimentoAndStatus(
                        LocalDate.now(),
                        StatusMovimentoDia.ABERTO
                )
                .orElseThrow(() ->
                        new BusinessException("Não existe movimento do dia aberto para registrar entrada de produto."));

        ProdutoEntity produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Produto não encontrado."));

        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Usuário não encontrado."));

        validarProdutoSemContagemFinal(produto.getId(), movimentoDia.getId());

        EntradaProdutoEntity entrada = EntradaProdutoMapperRecord.paraEntity(
                request,
                produto,
                usuario,
                movimentoDia
        );

        EntradaProdutoEntity entradaSalva = entradaProdutoRepository.save(entrada);

        Integer estoqueAtual = produto.getQuantidadeEstoque();

        if (estoqueAtual == null) {
            estoqueAtual = 0;
        }

        produto.setQuantidadeEstoque(estoqueAtual + request.quantidade());

        produtoRepository.save(produto);

        return EntradaProdutoMapperRecord.paraResponse(entradaSalva);
    }

    public List<EntradaProdutoResponseRecord> listar() {

        List<EntradaProdutoEntity> entradas = entradaProdutoRepository.findAll();

        return EntradaProdutoMapperRecord.paraListaResponse(entradas);
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
