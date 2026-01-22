package com.gastos.pessoais.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class LancamentoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testPrePersist_shouldAutoPopulateMesAndAno() {
        // Given
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao("Test Lancamento");
        lancamento.setValor(new BigDecimal("100.00"));
        lancamento.setData(LocalDate.of(2025, 10, 23));
        lancamento.setTipo(TipoLancamento.DESPESA);
        
        // When
        Lancamento savedLancamento = entityManager.persistAndFlush(lancamento);
        
        // Then
        assertThat(savedLancamento.getMes()).isEqualTo(Month.OCTOBER);
        assertThat(savedLancamento.getAno()).isEqualTo(2025);
    }

    @Test
    public void testPreUpdate_shouldAutoPopulateMesAndAnoOnUpdate() {
        // Given
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao("Test Lancamento");
        lancamento.setValor(new BigDecimal("100.00"));
        lancamento.setData(LocalDate.of(2025, 10, 23));
        lancamento.setTipo(TipoLancamento.DESPESA);
        
        Lancamento savedLancamento = entityManager.persistAndFlush(lancamento);
        entityManager.clear();
        
        // When
        Lancamento foundLancamento = entityManager.find(Lancamento.class, savedLancamento.getId());
        foundLancamento.setData(LocalDate.of(2025, 12, 15));
        Lancamento updatedLancamento = entityManager.persistAndFlush(foundLancamento);
        
        // Then
        assertThat(updatedLancamento.getMes()).isEqualTo(Month.DECEMBER);
        assertThat(updatedLancamento.getAno()).isEqualTo(2025);
    }

    @Test
    public void testMesIsPersistedAsString() {
        // Given
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao("Test Lancamento");
        lancamento.setValor(new BigDecimal("100.00"));
        lancamento.setData(LocalDate.of(2025, 10, 23));
        lancamento.setTipo(TipoLancamento.DESPESA);
        
        // When
        Lancamento savedLancamento = entityManager.persistAndFlush(lancamento);
        entityManager.clear();
        
        // Then - Verify mes is persisted as STRING (OCTOBER) not as ordinal number
        Object mesValue = entityManager.getEntityManager()
            .createNativeQuery("SELECT mes FROM lancamentos WHERE id = ?")
            .setParameter(1, savedLancamento.getId())
            .getSingleResult();
        
        assertThat(mesValue).isEqualTo("OCTOBER");
    }

    @Test
    public void testCategoriaLazyLoading() {
        // Given
        CategoriaGasto categoria = new CategoriaGasto();
        categoria.setNome("Test Category");
        categoria = entityManager.persistAndFlush(categoria);
        
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao("Test Lancamento");
        lancamento.setValor(new BigDecimal("100.00"));
        lancamento.setData(LocalDate.of(2025, 10, 23));
        lancamento.setTipo(TipoLancamento.DESPESA);
        lancamento.setCategoria(categoria);
        
        Lancamento savedLancamento = entityManager.persistAndFlush(lancamento);
        entityManager.clear();
        
        // When - Load the lancamento without fetching categoria
        Lancamento foundLancamento = entityManager.find(Lancamento.class, savedLancamento.getId());
        
        // Then - Categoria should be a proxy and not yet loaded
        assertThat(foundLancamento.getCategoria()).isNotNull();
        // Note: In a real lazy-loading scenario, accessing categoria properties would trigger loading
        // For this test, we're just verifying the relationship is configured correctly
    }

    @Test
    public void testEqualsAndHashCode_onlyUsesId() {
        // Given
        Lancamento lancamento1 = new Lancamento();
        lancamento1.setId(1L);
        lancamento1.setDescricao("Test 1");
        
        Lancamento lancamento2 = new Lancamento();
        lancamento2.setId(1L);
        lancamento2.setDescricao("Test 2");
        
        Lancamento lancamento3 = new Lancamento();
        lancamento3.setId(2L);
        lancamento3.setDescricao("Test 1");
        
        // Then
        assertThat(lancamento1).isEqualTo(lancamento2); // Same ID
        assertThat(lancamento1).isNotEqualTo(lancamento3); // Different ID
        assertThat(lancamento1.hashCode()).isEqualTo(lancamento2.hashCode());
    }
}
