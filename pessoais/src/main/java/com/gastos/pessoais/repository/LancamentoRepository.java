package com.gastos.pessoais.repository;

import com.gastos.pessoais.model.Lancamento;
import com.gastos.pessoais.model.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    
    List<Lancamento> findByMesAndAnoOrderByDataDesc(Month mes, int ano);
    
    @Query("SELECT l FROM Lancamento l WHERE YEAR(l.data) = :ano AND MONTH(l.data) = :mes ORDER BY l.data DESC")
    List<Lancamento> findByMesAndAno(@Param("mes") int mes, @Param("ano") int ano);
    
    @Query("SELECT l FROM Lancamento l WHERE YEAR(l.data) = :ano AND MONTH(l.data) = :mes AND l.tipo = :tipo ORDER BY l.data DESC")
    List<Lancamento> findByMesAnoAndTipo(
        @Param("mes") int mes, 
        @Param("ano") int ano, 
        @Param("tipo") TipoLancamento tipo
    );
    
    List<Lancamento> findByCategoriaId(Long categoriaId);
    
    @Query("SELECT l FROM Lancamento l WHERE l.categoria.id = :categoriaId AND YEAR(l.data) = :ano AND MONTH(l.data) = :mes")
    List<Lancamento> findByCategoriaIdAndMesAndAno(
        @Param("categoriaId") Long categoriaId, 
        @Param("mes") int mes, 
        @Param("ano") int ano
    );
    
    @Query("SELECT COALESCE(SUM(l.valor), 0) FROM Lancamento l WHERE YEAR(l.data) = :ano AND MONTH(l.data) = :mes")
    BigDecimal calcularTotalPorMesEAno(@Param("mes") int mes, @Param("ano") int ano);
    
    @Query("SELECT COALESCE(SUM(l.valor), 0) FROM Lancamento l WHERE YEAR(l.data) = :ano AND MONTH(l.data) = :mes AND l.tipo = :tipo")
    BigDecimal calcularTotalPorMesAnoETipo(
        @Param("mes") int mes, 
        @Param("ano") int ano, 
        @Param("tipo") TipoLancamento tipo
    );
}
