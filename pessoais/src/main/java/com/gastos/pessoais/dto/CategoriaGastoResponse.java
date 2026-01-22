package com.gastos.pessoais.dto;

import lombok.Data;

@Data
public class CategoriaGastoResponse {
    
    private Long id;
    
    private String nome;
    
    private String descricao;
    
    private String observacao;
}
