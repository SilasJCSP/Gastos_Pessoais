package com.gastos.pessoais.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LancamentoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void deveSincronizarMesEAnoAoSalvar() {
        // Given
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao("Teste de lançamento");
        lancamento.setValor(new BigDecimal("100.00"));
        lancamento.setData(LocalDate.of(2024, 3, 15));
        lancamento.setTipo(TipoLancamento.DESPESA);

        // When
        Lancamento saved = entityManager.persistAndFlush(lancamento);

        // Then
        assertThat(saved.getMes()).isEqualTo(Month.MARCH);
        assertThat(saved.getAno()).isEqualTo(2024);
    }

    @Test
    void deveSincronizarMesEAnoAoAtualizar() {
        // Given
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao("Teste de lançamento");
        lancamento.setValor(new BigDecimal("100.00"));
        lancamento.setData(LocalDate.of(2024, 3, 15));
        lancamento.setTipo(TipoLancamento.DESPESA);
        
        Lancamento saved = entityManager.persistAndFlush(lancamento);
        entityManager.clear();

        // When
        Lancamento found = entityManager.find(Lancamento.class, saved.getId());
        found.setData(LocalDate.of(2024, 7, 20));
        entityManager.flush();
        entityManager.clear();

        // Then
        Lancamento updated = entityManager.find(Lancamento.class, saved.getId());
        assertThat(updated.getMes()).isEqualTo(Month.JULY);
        assertThat(updated.getAno()).isEqualTo(2024);
    }

    @Test
    void deveArmazenarMesComoString() {
        // Given
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao("Teste de lançamento");
        lancamento.setValor(new BigDecimal("100.00"));
        lancamento.setData(LocalDate.of(2024, 12, 31));
        lancamento.setTipo(TipoLancamento.RECEITA);

        // When
        Lancamento saved = entityManager.persistAndFlush(lancamento);
        entityManager.clear();

        // Then
        Object mesValue = entityManager.getEntityManager()
                .createNativeQuery("SELECT mes FROM lancamentos WHERE id = ?")
                .setParameter(1, saved.getId())
                .getSingleResult();
        
        assertThat(mesValue).isEqualTo("DECEMBER");
    }

    @Test
    void deveUsarApenasIdNoEquals() {
        // Given
        Lancamento lancamento1 = new Lancamento();
        lancamento1.setId(1L);
        lancamento1.setDescricao("Teste 1");

        Lancamento lancamento2 = new Lancamento();
        lancamento2.setId(1L);
        lancamento2.setDescricao("Teste 2");

        Lancamento lancamento3 = new Lancamento();
        lancamento3.setId(2L);
        lancamento3.setDescricao("Teste 1");

        // Then
        assertThat(lancamento1).isEqualTo(lancamento2); // Mesmo ID
        assertThat(lancamento1).isNotEqualTo(lancamento3); // ID diferente
    }

    @Test
    void deveUsarApenasIdNoHashCode() {
        // Given
        Lancamento lancamento1 = new Lancamento();
        lancamento1.setId(1L);
        lancamento1.setDescricao("Teste 1");

        Lancamento lancamento2 = new Lancamento();
        lancamento2.setId(1L);
        lancamento2.setDescricao("Teste 2");

        // Then
        assertThat(lancamento1.hashCode()).isEqualTo(lancamento2.hashCode());
    }
}
