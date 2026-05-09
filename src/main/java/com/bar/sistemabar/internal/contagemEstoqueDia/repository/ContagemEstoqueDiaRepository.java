package com.bar.sistemabar.internal.contagemEstoqueDia.repository;

import com.bar.sistemabar.internal.contagemEstoqueDia.entity.ContagemEstoqueDiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContagemEstoqueDiaRepository
        extends JpaRepository<ContagemEstoqueDiaEntity, Long> {

    Optional<ContagemEstoqueDiaEntity>
    findByProdutoIdAndMovimentoDiaId(Long produtoId, Long movimentoDiaId);

    List<ContagemEstoqueDiaEntity>
    findByMovimentoDiaId(Long movimentoDiaId);

}