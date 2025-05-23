package com.compras.livrariaapi.service;

import com.compras.livrariaapi.model.request.LivroRequest;
import com.compras.livrariaapi.model.response.LivroResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface LivroService {
    LivroResponse cadastrarLivro(@Valid LivroRequest request);

    List<LivroResponse> listarLivros();

    LivroResponse consultarLivro(Long id);

    LivroResponse atualizarLivro(Long id, @Valid LivroRequest request);

    LivroResponse removerLivro(Long id);
}