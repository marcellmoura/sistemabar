package com.bar.sistemabar.internal.entradaProduto.repository;

import com.bar.sistemabar.internal.entradaProduto.entity.EntradaProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaProdutoRepository extends JpaRepository<EntradaProdutoEntity, Long> {
}