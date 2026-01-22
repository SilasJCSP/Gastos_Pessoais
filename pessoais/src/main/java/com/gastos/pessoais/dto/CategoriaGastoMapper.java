package com.gastos.pessoais.dto;

import com.gastos.pessoais.model.CategoriaGasto;

public class CategoriaGastoMapper {
    
    /**
     * Converts a CategoriaGastoRequest to a CategoriaGasto entity.
     * @param request the request DTO
     * @return the CategoriaGasto entity
     */
    public static CategoriaGasto toEntity(CategoriaGastoRequest request) {
        if (request == null) {
            return null;
        }
        
        CategoriaGasto categoria = new CategoriaGasto();
        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());
        categoria.setObservacao(request.getObservacao());
        
        return categoria;
    }
    
    /**
     * Converts a CategoriaGasto entity to a CategoriaGastoResponse.
     * @param categoria the CategoriaGasto entity
     * @return the response DTO
     */
    public static CategoriaGastoResponse toResponse(CategoriaGasto categoria) {
        if (categoria == null) {
            return null;
        }
        
        CategoriaGastoResponse response = new CategoriaGastoResponse();
        response.setId(categoria.getId());
        response.setNome(categoria.getNome());
        response.setDescricao(categoria.getDescricao());
        response.setObservacao(categoria.getObservacao());
        
        return response;
    }
    
    /**
     * Updates an existing CategoriaGasto entity with data from a CategoriaGastoRequest.
     * @param categoria the existing entity to update
     * @param request the request DTO with new data
     */
    public static void updateEntityFromRequest(CategoriaGasto categoria, CategoriaGastoRequest request) {
        if (categoria == null || request == null) {
            return;
        }
        
        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());
        categoria.setObservacao(request.getObservacao());
    }
}
