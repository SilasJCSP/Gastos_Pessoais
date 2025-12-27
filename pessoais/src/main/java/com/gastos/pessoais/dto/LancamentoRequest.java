package com.gastos.pessoais.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gastos.pessoais.model.TipoLancamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Data
public class LancamentoRequest {

    @NotNull(message = "Data é obrigatória")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate data;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    private Long categoriaId;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "Tipo é obrigatório")
    private TipoLancamento tipo;

    public void setMesReferencia(String mesReferencia) {
        if (mesReferencia != null && !mesReferencia.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
            YearMonth yearMonth = YearMonth.parse(mesReferencia, formatter);
            this.data = yearMonth.atDay(1);
        }
    }
}
