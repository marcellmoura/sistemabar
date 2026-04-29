package com.bar.sistemabar.internal.categoria.repository;

import com.bar.sistemabar.internal.categoria.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
}