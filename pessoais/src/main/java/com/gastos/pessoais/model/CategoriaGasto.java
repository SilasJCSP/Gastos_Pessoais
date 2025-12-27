package com.gastos.pessoais.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "categorias")
@Data
public class CategoriaGasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome; // Ex: Prestaçao AP, Seguro Carro, Fatura Santander
    
    @Column(columnDefinition = "TEXT")
    private String descricao; // Descrição detalhada da categoria
    
    @Column(columnDefinition = "TEXT")
    private String observacao; // Informações adicionais
}
