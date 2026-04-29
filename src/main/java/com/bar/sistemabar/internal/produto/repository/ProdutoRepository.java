package com.bar.sistemabar.internal.produto.repository;

import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
}