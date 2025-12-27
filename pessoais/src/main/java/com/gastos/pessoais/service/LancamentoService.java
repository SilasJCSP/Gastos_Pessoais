package com.gastos.pessoais.service;

import com.gastos.pessoais.dto.LancamentoRequest;
import com.gastos.pessoais.dto.LancamentoResponse;
import com.gastos.pessoais.exception.CategoriaNaoEncontradaException;
import com.gastos.pessoais.model.CategoriaGasto;
import com.gastos.pessoais.model.Lancamento;
import com.gastos.pessoais.model.TipoLancamento;
import com.gastos.pessoais.repository.CategoriaGastoRepository;
import com.gastos.pessoais.repository.LancamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LancamentoService {

    private final LancamentoRepository lancamentoRepository;
    private final CategoriaGastoRepository categoriaGastoRepository;

    @Transactional
    public LancamentoResponse criar(LancamentoRequest request) {
        CategoriaGasto categoria = request.getCategoriaId() != null ? 
                buscarCategoriaPorId(request.getCategoriaId()) : null;
        
        Lancamento lancamento = new Lancamento();
        lancamento.setData(request.getData());
        lancamento.setDescricao(request.getDescricao());
        lancamento.setCategoria(categoria);
        lancamento.setValor(request.getValor());
        lancamento.setTipo(request.getTipo());
        
        // Define mês e ano com base na data
        lancamento.setMes(request.getData().getMonth());
        lancamento.setAno(request.getData().getYear());
        
        return new LancamentoResponse(lancamentoRepository.save(lancamento));
    }

    @Transactional(readOnly = true)
    public LancamentoResponse buscarPorId(Long id) {
        return new LancamentoResponse(buscarLancamentoPorId(id));
    }

    @Transactional(readOnly = true)
    public Page<LancamentoResponse> listar(Pageable pageable) {
        return lancamentoRepository.findAll(pageable)
                .map(LancamentoResponse::new);
    }

    @Transactional
    public LancamentoResponse atualizar(Long id, LancamentoRequest request) {
        Lancamento lancamento = buscarLancamentoPorId(id);
        CategoriaGasto categoria = request.getCategoriaId() != null ? 
                buscarCategoriaPorId(request.getCategoriaId()) : null;
        
        lancamento.setData(request.getData());
        lancamento.setDescricao(request.getDescricao());
        lancamento.setCategoria(categoria);
        lancamento.setValor(request.getValor());
        lancamento.setTipo(request.getTipo());
        
        // Atualiza mês e ano com base na data
        lancamento.setMes(request.getData().getMonth());
        lancamento.setAno(request.getData().getYear());
        
        return new LancamentoResponse(lancamentoRepository.save(lancamento));
    }

    @Transactional
    public void excluir(Long id) {
        Lancamento lancamento = buscarLancamentoPorId(id);
        lancamentoRepository.delete(lancamento);
    }

    @Transactional(readOnly = true)
    public List<LancamentoResponse> listarPorMesAno(int mes, int ano) {
        return lancamentoRepository.findByMesAndAno(mes, ano).stream()
                .map(LancamentoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<String, BigDecimal> calcularTotalPorCategoria(int mes, int ano) {
        List<Lancamento> lancamentos = lancamentoRepository.findByMesAndAno(mes, ano);
        
        return lancamentos.stream()
                .filter(l -> l.getCategoria() != null) // Filtra lançamentos sem categoria
                .collect(Collectors.groupingBy(
                        l -> l.getCategoria().getNome(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Lancamento::getValor,
                                BigDecimal::add
                        )
                ));
    }

    @Transactional(readOnly = true)
    public Map<TipoLancamento, BigDecimal> calcularTotalPorTipo(int mes, int ano) {
        List<Lancamento> lancamentos = lancamentoRepository.findByMesAndAno(mes, ano);
        
        return lancamentos.stream()
                .collect(Collectors.groupingBy(
                        Lancamento::getTipo,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Lancamento::getValor,
                                BigDecimal::add
                        )
                ));
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularTotalMensal(int mes, int ano) {
        return lancamentoRepository.findByMesAndAno(mes, ano).stream()
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Lancamento buscarLancamentoPorId(Long id) {
        return lancamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lançamento não encontrado com o ID: " + id));
    }

    private CategoriaGasto buscarCategoriaPorId(Long id) {
        return categoriaGastoRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
    }
}
