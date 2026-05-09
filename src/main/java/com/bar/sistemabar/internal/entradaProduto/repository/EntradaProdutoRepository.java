package com.bar.sistemabar.internal.entradaProduto.repository;

import com.bar.sistemabar.internal.entradaProduto.entity.EntradaProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntradaProdutoRepository extends JpaRepository<EntradaProdutoEntity, Long> {

    List<EntradaProdutoEntity> findByMovimentoDiaId(Long movimentoDiaId);

}