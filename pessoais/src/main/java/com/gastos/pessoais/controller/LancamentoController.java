package com.gastos.pessoais.controller;

import com.gastos.pessoais.dto.LancamentoRequest;
import com.gastos.pessoais.dto.LancamentoResponse;
import com.gastos.pessoais.model.TipoLancamento;
import com.gastos.pessoais.service.LancamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoController {

    private final LancamentoService lancamentoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LancamentoResponse criar(@Valid @RequestBody LancamentoRequest request) {
        return lancamentoService.criar(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(lancamentoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<LancamentoResponse>> listar(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(lancamentoService.listar(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoResponse> atualizar(
            @PathVariable Long id, 
            @Valid @RequestBody LancamentoRequest request
    ) {
        return ResponseEntity.ok(lancamentoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        lancamentoService.excluir(id);
    }

    @GetMapping("/mes/{ano}/{mes}")
    public ResponseEntity<List<LancamentoResponse>> listarPorMesAno(
            @PathVariable int ano,
            @PathVariable int mes
    ) {
        validarMes(mes);
        return ResponseEntity.ok(lancamentoService.listarPorMesAno(mes, ano));
    }

    @GetMapping("/resumo/{ano}/{mes}")
    public ResponseEntity<Map<String, Object>> resumoMensal(
            @PathVariable int ano,
            @PathVariable int mes
    ) {
        validarMes(mes);
        
        Map<String, BigDecimal> totalPorCategoria = lancamentoService.calcularTotalPorCategoria(mes, ano);
        Map<TipoLancamento, BigDecimal> totalPorTipo = lancamentoService.calcularTotalPorTipo(mes, ano);
        
        return ResponseEntity.ok(Map.of(
            "ano", ano,
            "mes", mes,
            "totalPorCategoria", totalPorCategoria,
            "totalPorTipo", totalPorTipo
        ));
    }
    
    private void validarMes(int mes) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês deve estar entre 1 e 12");
        }
    }
}
