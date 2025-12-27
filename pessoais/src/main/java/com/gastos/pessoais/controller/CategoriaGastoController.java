package com.gastos.pessoais.controller;

import com.gastos.pessoais.model.CategoriaGasto;
import com.gastos.pessoais.service.CategoriaGastoService;
import com.gastos.pessoais.service.ExportService;
//import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaGastoController {

    private final CategoriaGastoService categoriaGastoService;
    private final ExportService exportService;

    public CategoriaGastoController(CategoriaGastoService categoriaGastoService, ExportService exportService) {
        this.categoriaGastoService = categoriaGastoService;
        this.exportService = exportService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaGasto criar(@Validated @RequestBody CategoriaGasto categoria) {
        return categoriaGastoService.criar(categoria);
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaGasto>> listar(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable
    ) {
        return ResponseEntity.ok(categoriaGastoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaGasto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaGastoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaGasto> atualizar(
            @PathVariable Long id, 
            @Validated @RequestBody CategoriaGasto categoriaAtualizada
    ) {
        return ResponseEntity.ok(categoriaGastoService.atualizar(id, categoriaAtualizada));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        categoriaGastoService.excluir(id);
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportarParaExcel() {
        List<CategoriaGasto> categorias = categoriaGastoService.listarTodos();
        ByteArrayInputStream in = exportService.exportarCategoriasParaExcel(categorias);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=categorias.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(in));
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<InputStreamResource> exportarParaPdf() {
        List<CategoriaGasto> categorias = categoriaGastoService.listarTodos();
        ByteArrayInputStream in = exportService.exportarCategoriasParaPdf(categorias);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=categorias.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(in));
    }
}
