package com.bar.sistemabar.internal.entradaProduto.service;

import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.entradaProduto.dto.EntradaProdutoRequestRecord;
import com.bar.sistemabar.internal.entradaProduto.dto.EntradaProdutoResponseRecord;
import com.bar.sistemabar.internal.entradaProduto.entity.EntradaProdutoEntity;
import com.bar.sistemabar.internal.entradaProduto.mapper.EntradaProdutoMapperRecord;
import com.bar.sistemabar.internal.entradaProduto.repository.EntradaProdutoRepository;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.produto.repository.ProdutoRepository;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import com.bar.sistemabar.internal.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntradaProdutoService {

    private final EntradaProdutoRepository entradaProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public EntradaProdutoService(
            EntradaProdutoRepository entradaProdutoRepository,
            ProdutoRepository produtoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.entradaProdutoRepository = entradaProdutoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public EntradaProdutoResponseRecord cadastrar(EntradaProdutoRequestRecord request) {

        ProdutoEntity produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Produto não encontrado."));

        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Usuário não encontrado."));

        EntradaProdutoEntity entrada = EntradaProdutoMapperRecord.paraEntity(
                request,
                produto,
                usuario
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
}