package com.bar.sistemabar.internal.movimentoDia.repository;

import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.movimentoDia.entity.StatusMovimentoDia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MovimentoDiaRepository extends JpaRepository<MovimentoDiaEntity, Long> {

    Optional<MovimentoDiaEntity> findByDataMovimentoAndStatus(
            LocalDate dataMovimento,
            StatusMovimentoDia status
    );

    Optional<MovimentoDiaEntity> findByIdAndStatus(
            Long id,
            StatusMovimentoDia status
    );

    boolean existsByDataMovimentoAndStatus(
            LocalDate dataMovimento,
            StatusMovimentoDia status
    );

}