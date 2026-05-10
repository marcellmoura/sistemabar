package com.bar.sistemabar.internal.fiado.repository;

import com.bar.sistemabar.internal.fiado.entity.PessoaFiadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaFiadoRepository extends JpaRepository<PessoaFiadoEntity, Long> {

    boolean existsByNome(String nome);

}
