package com.bar.sistemabar.internal.produto.repository;

import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.produto.entity.StatusProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    boolean existsByNome(String nome);

    List<ProdutoEntity> findByControlaEstoqueTrueAndStatus(StatusProduto status);

}
