package com.gastos.pessoais.exception;

public class CategoriaNaoEncontradaException extends RuntimeException {
    
    public CategoriaNaoEncontradaException(Long id) {
        super(String.format("Categoria com ID %d não encontrada", id));
    }
}
