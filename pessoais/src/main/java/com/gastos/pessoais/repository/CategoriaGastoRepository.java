package com.gastos.pessoais.repository;

import com.gastos.pessoais.model.CategoriaGasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaGastoRepository extends JpaRepository<CategoriaGasto, Long> {
    boolean existsByNome(String nome);
}
