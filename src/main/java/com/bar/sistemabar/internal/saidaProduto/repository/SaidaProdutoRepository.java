package com.bar.sistemabar.internal.saidaProduto.repository;

import com.bar.sistemabar.internal.saidaProduto.entity.SaidaProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaidaProdutoRepository extends JpaRepository<SaidaProdutoEntity, Long> {

    List<SaidaProdutoEntity> findByMovimentoDiaId(Long movimentoDiaId);

}
