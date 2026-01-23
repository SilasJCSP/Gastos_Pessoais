package com.gastos.pessoais.controller;

import com.gastos.pessoais.dto.CategoriaGastoMapper;
import com.gastos.pessoais.dto.CategoriaGastoRequest;
import com.gastos.pessoais.dto.CategoriaGastoResponse;
import com.gastos.pessoais.model.CategoriaGasto;
import com.gastos.pessoais.service.CategoriaGastoService;
import com.gastos.pessoais.service.ExportService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public CategoriaGastoResponse criar(@Valid @RequestBody CategoriaGastoRequest request) {
        CategoriaGasto categoria = CategoriaGastoMapper.toEntity(request);
        CategoriaGasto categoriaSalva = categoriaGastoService.criar(categoria);
        return CategoriaGastoMapper.toResponse(categoriaSalva);
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaGastoResponse>> listar(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable
    ) {
        Page<CategoriaGasto> categorias = categoriaGastoService.listar(pageable);
        Page<CategoriaGastoResponse> categoriasResponse = categorias.map(CategoriaGastoMapper::toResponse);
        return ResponseEntity.ok(categoriasResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaGastoResponse> buscarPorId(@PathVariable Long id) {
        CategoriaGasto categoria = categoriaGastoService.buscarPorId(id);
        return ResponseEntity.ok(CategoriaGastoMapper.toResponse(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaGastoResponse> atualizar(
            @PathVariable Long id, 
            @Valid @RequestBody CategoriaGastoRequest request
    ) {
        CategoriaGasto categoriaAtualizada = CategoriaGastoMapper.toEntity(request);
        CategoriaGasto categoriaSalva = categoriaGastoService.atualizar(id, categoriaAtualizada);
        return ResponseEntity.ok(CategoriaGastoMapper.toResponse(categoriaSalva));
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
