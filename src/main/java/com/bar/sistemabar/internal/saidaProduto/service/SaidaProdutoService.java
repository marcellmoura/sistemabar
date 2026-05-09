package com.bar.sistemabar.internal.saidaProduto.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.movimentoDia.entity.StatusMovimentoDia;
import com.bar.sistemabar.internal.movimentoDia.repository.MovimentoDiaRepository;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.produto.repository.ProdutoRepository;
import com.bar.sistemabar.internal.saidaProduto.dto.SaidaProdutoRequestRecord;
import com.bar.sistemabar.internal.saidaProduto.dto.SaidaProdutoResponseRecord;
import com.bar.sistemabar.internal.saidaProduto.entity.SaidaProdutoEntity;
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

    public SaidaProdutoService(SaidaProdutoRepository saidaProdutoRepository,
                               ProdutoRepository produtoRepository,
                               UsuarioRepository usuarioRepository,
                               MovimentoDiaRepository movimentoDiaRepository) {
        this.saidaProdutoRepository = saidaProdutoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.movimentoDiaRepository = movimentoDiaRepository;
    }

    @Transactional
    public SaidaProdutoResponseRecord registrar(SaidaProdutoRequestRecord request) {

        MovimentoDiaEntity movimentoDia = movimentoDiaRepository
                .findByDataMovimentoAndStatus(
                        LocalDate.now(),
                        StatusMovimentoDia.ABERTO
                )
                .orElseThrow(() ->
                        new BusinessException("Não existe movimento do dia aberto para registrar saída de produto."));

        ProdutoEntity produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Produto não encontrado."));

        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Usuário não encontrado."));

        if (produto.getQuantidadeEstoque() < request.quantidade()) {
            throw new BusinessException("Estoque insuficiente para realizar a saída.");
        }

        produto.setQuantidadeEstoque(
                produto.getQuantidadeEstoque() - request.quantidade()
        );

        SaidaProdutoEntity entity = new SaidaProdutoEntity(
                request.quantidade(),
                request.tipoSaida(),
                LocalDateTime.now(),
                produto,
                usuario,
                movimentoDia
        );

        SaidaProdutoEntity saidaSalva = saidaProdutoRepository.save(entity);

        return SaidaProdutoMapperRecord.entityToResponseRecord(saidaSalva);
    }

    public List<SaidaProdutoResponseRecord> listar() {
        return SaidaProdutoMapperRecord.entityListToResponseList(
                saidaProdutoRepository.findAll()
        );
    }
}