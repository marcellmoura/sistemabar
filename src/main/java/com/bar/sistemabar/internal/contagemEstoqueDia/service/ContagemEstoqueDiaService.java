package com.bar.sistemabar.internal.contagemEstoqueDia.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.contagemEstoqueDia.dto.ContagemEstoqueDiaRequestRecord;
import com.bar.sistemabar.internal.contagemEstoqueDia.dto.ContagemEstoqueDiaResponseRecord;
import com.bar.sistemabar.internal.contagemEstoqueDia.dto.ContagemFinalRequestRecord;
import com.bar.sistemabar.internal.contagemEstoqueDia.entity.ContagemEstoqueDiaEntity;
import com.bar.sistemabar.internal.contagemEstoqueDia.mapper.ContagemEstoqueDiaMapperRecord;
import com.bar.sistemabar.internal.contagemEstoqueDia.repository.ContagemEstoqueDiaRepository;
import com.bar.sistemabar.internal.entradaProduto.entity.EntradaProdutoEntity;
import com.bar.sistemabar.internal.entradaProduto.repository.EntradaProdutoRepository;
import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.movimentoDia.repository.MovimentoDiaRepository;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.produto.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContagemEstoqueDiaService {

    private final ContagemEstoqueDiaRepository contagemEstoqueDiaRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentoDiaRepository movimentoDiaRepository;
    private final EntradaProdutoRepository entradaProdutoRepository;

    public ContagemEstoqueDiaService(
            ContagemEstoqueDiaRepository contagemEstoqueDiaRepository,
            ProdutoRepository produtoRepository,
            MovimentoDiaRepository movimentoDiaRepository,
            EntradaProdutoRepository entradaProdutoRepository
    ) {

        this.contagemEstoqueDiaRepository = contagemEstoqueDiaRepository;
        this.produtoRepository = produtoRepository;
        this.movimentoDiaRepository = movimentoDiaRepository;
        this.entradaProdutoRepository = entradaProdutoRepository;
    }

    @Transactional
    public ContagemEstoqueDiaResponseRecord registrarContagemInicial(
            ContagemEstoqueDiaRequestRecord request
    ) {

        ProdutoEntity produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Produto não encontrado")
                );

        MovimentoDiaEntity movimentoDia = movimentoDiaRepository
                .findById(request.movimentoDiaId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Movimento do dia não encontrado")
                );

        if (movimentoDia.getDataHoraFechamento() != null) {
            throw new BusinessException(
                    "Não é possível registrar contagem em um movimento já fechado"
            );
        }

        boolean jaExisteContagem = contagemEstoqueDiaRepository
                .findByProdutoIdAndMovimentoDiaId(
                        produto.getId(),
                        movimentoDia.getId()
                )
                .isPresent();

        if (jaExisteContagem) {
            throw new BusinessException(
                    "Já existe contagem registrada para este produto neste movimento"
            );
        }

        ContagemEstoqueDiaEntity contagem = new ContagemEstoqueDiaEntity();

        contagem.setProduto(produto);
        contagem.setMovimentoDia(movimentoDia);

        contagem.setQuantidadeInicial(request.quantidadeInicial());

        contagem.setQuantidadeFinal(null);
        contagem.setQuantidadeVendidaCalculada(null);
        contagem.setValorVendidoCalculado(null);

        ContagemEstoqueDiaEntity contagemSalva =
                contagemEstoqueDiaRepository.save(contagem);

        return ContagemEstoqueDiaMapperRecord
                .entityToResponse(contagemSalva);
    }

    @Transactional
    public ContagemEstoqueDiaResponseRecord registrarContagemFinal(
            Long contagemId,
            ContagemFinalRequestRecord request
    ) {

        ContagemEstoqueDiaEntity contagem = contagemEstoqueDiaRepository
                .findById(contagemId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Contagem não encontrada")
                );

        if (contagem.getMovimentoDia().getDataHoraFechamento() != null) {
            throw new BusinessException(
                    "Não é possível alterar contagem de movimento fechado"
            );
        }

        contagem.setQuantidadeFinal(request.quantidadeFinal());

        List<EntradaProdutoEntity> entradas =
                entradaProdutoRepository.findByMovimentoDiaId(
                        contagem.getMovimentoDia().getId()
                );

        int totalEntradasProduto = entradas.stream()
                .filter(entrada ->
                        entrada.getProduto().getId()
                                .equals(contagem.getProduto().getId())
                )
                .mapToInt(EntradaProdutoEntity::getQuantidade)
                .sum();

        int quantidadeVendida =
                contagem.getQuantidadeInicial()
                        + totalEntradasProduto
                        - contagem.getQuantidadeFinal();

        contagem.setQuantidadeVendidaCalculada(quantidadeVendida);

        double valorVendido =
                quantidadeVendida * contagem.getProduto().getPreco();

        contagem.setValorVendidoCalculado(valorVendido);

        ContagemEstoqueDiaEntity contagemAtualizada =
                contagemEstoqueDiaRepository.save(contagem);

        return ContagemEstoqueDiaMapperRecord
                .entityToResponse(contagemAtualizada);
    }
}