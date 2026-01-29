package com.gastos.pessoais.dto;

public record LoginResponse(String token, String tipo, Long id, String email) {}
