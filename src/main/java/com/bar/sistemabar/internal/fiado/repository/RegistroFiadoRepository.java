package com.bar.sistemabar.internal.fiado.repository;

import com.bar.sistemabar.internal.fiado.entity.RegistroFiadoEntity;
import com.bar.sistemabar.internal.fiado.entity.StatusFiado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroFiadoRepository extends JpaRepository<RegistroFiadoEntity, Long> {

    List<RegistroFiadoEntity> findByPessoaFiadoId(Long pessoaFiadoId);

    List<RegistroFiadoEntity> findByStatus(StatusFiado status);

    List<RegistroFiadoEntity> findByMovimentoDiaId(Long movimentoDiaId);

}
