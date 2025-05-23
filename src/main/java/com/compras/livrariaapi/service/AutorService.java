package com.compras.livrariaapi.service;

import com.compras.livrariaapi.model.request.AutorRequest;
import com.compras.livrariaapi.model.response.AutorResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface AutorService {
    List<AutorResponse> listarAutor();

    AutorResponse cadastrarAutor(@Valid AutorRequest request);

    AutorResponse consultarAutor(Long id);

    AutorResponse atualizarAutor(Long id, @Valid AutorRequest request);

    AutorResponse removerAutor(Long id);
}
