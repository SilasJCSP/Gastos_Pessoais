package com.gastos.pessoais.service;

import com.gastos.pessoais.exception.CategoriaNaoEncontradaException;
import com.gastos.pessoais.model.CategoriaGasto;
import com.gastos.pessoais.repository.CategoriaGastoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoriaGastoService {

    private final CategoriaGastoRepository categoriaGastoRepository;

    @Transactional
    public CategoriaGasto criar(CategoriaGasto categoria) {
        return categoriaGastoRepository.save(categoria);
    }

    @Transactional(readOnly = true)
    public Page<CategoriaGasto> listar(Pageable pageable) {
        return categoriaGastoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<CategoriaGasto> listarTodos() {
        return categoriaGastoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CategoriaGasto buscarPorId(Long id) {
        return categoriaGastoRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
    }

    @Transactional
    public CategoriaGasto atualizar(Long id, CategoriaGasto categoriaAtualizada) {
        return categoriaGastoRepository.findById(id)
                .map(categoria -> {
                    categoria.setNome(categoriaAtualizada.getNome());
                    categoria.setDescricao(categoriaAtualizada.getDescricao());
                    categoria.setObservacao(categoriaAtualizada.getObservacao());
                    return categoriaGastoRepository.save(categoria);
                })
                .orElseGet(() -> {
                    categoriaAtualizada.setId(id);
                    return categoriaGastoRepository.save(categoriaAtualizada);
                });
    }

    @Transactional
    public void excluir(Long id) {
        if (!categoriaGastoRepository.existsById(id)) {
            throw new CategoriaNaoEncontradaException(id);
        }
        categoriaGastoRepository.deleteById(id);
    }
}
