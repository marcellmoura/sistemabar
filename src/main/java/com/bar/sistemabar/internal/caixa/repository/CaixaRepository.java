package com.bar.sistemabar.internal.caixa.repository;

import com.bar.sistemabar.internal.caixa.entity.CaixaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaixaRepository extends JpaRepository<CaixaEntity, Long> {

    boolean existsByMovimentoDiaId(Long movimentoDiaId);

    Optional<CaixaEntity> findByMovimentoDiaId(Long movimentoDiaId);

}
