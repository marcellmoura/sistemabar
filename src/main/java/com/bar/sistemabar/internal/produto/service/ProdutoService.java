package com.bar.sistemabar.internal.produto.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.categoria.entity.CategoriaEntity;
import com.bar.sistemabar.internal.categoria.repository.CategoriaRepository;
import com.bar.sistemabar.internal.produto.dto.ProdutoRequestRecord;
import com.bar.sistemabar.internal.produto.dto.ProdutoResponseRecord;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.produto.mapper.ProdutoMapperRecord;
import com.bar.sistemabar.internal.produto.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                          CategoriaRepository categoriaRepository) {

        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public ProdutoResponseRecord cadastrar(ProdutoRequestRecord request) {

        if (produtoRepository.existsByNome(request.nome())) {
            throw new BusinessException("Já existe um produto com esse nome.");
        }

        CategoriaEntity categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Categoria não encontrada."));

        ProdutoEntity produto = ProdutoMapperRecord.paraEntity(request, categoria);

        ProdutoEntity produtoSalvo = produtoRepository.save(produto);

        return ProdutoMapperRecord.paraResponse(produtoSalvo);
    }

    public List<ProdutoResponseRecord> listar() {

        List<ProdutoEntity> produtos = produtoRepository.findAll();

        return ProdutoMapperRecord.paraListaResponse(produtos);
    }
}