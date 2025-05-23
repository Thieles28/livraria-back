package com.compras.livrariaapi.service;

import com.compras.livrariaapi.model.request.GeneroRequest;
import com.compras.livrariaapi.model.response.GeneroResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface GeneroService {
    List<GeneroResponse> listarGenero();

    GeneroResponse cadastrarGenero(@Valid GeneroRequest request);

    GeneroResponse consultarGenero(Long id);

    GeneroResponse atualizarGenero(Long id, @Valid GeneroRequest request);

    GeneroResponse removerGenero(Long id);
}