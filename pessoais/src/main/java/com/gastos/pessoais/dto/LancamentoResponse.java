package com.gastos.pessoais.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gastos.pessoais.model.Lancamento;
import com.gastos.pessoais.model.TipoLancamento;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class LancamentoResponse {
    private Long id;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
    
    private String descricao;
    private String categoria;
    private BigDecimal valor;
    private TipoLancamento tipo;
    private String mesReferencia;
    
    public LancamentoResponse(Lancamento lancamento) {
        this.id = lancamento.getId();
        this.data = lancamento.getData();
        this.descricao = lancamento.getDescricao();
        this.categoria = lancamento.getCategoria() != null ? lancamento.getCategoria().getNome() : null;
        this.valor = lancamento.getValor();
        this.tipo = lancamento.getTipo();
        
        // Formatar mês/ano para exibição
        if (lancamento.getData() != null) {
            this.mesReferencia = lancamento.getData().format(DateTimeFormatter.ofPattern("MM/yyyy"));
        }
    }
}
