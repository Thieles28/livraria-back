package com.compras.livrariaapi.factory;

import com.compras.livrariaapi.entity.Autor;
import com.compras.livrariaapi.model.request.AutorRequest;
import com.compras.livrariaapi.model.response.AutorResponse;

import java.util.ArrayList;

public class AutorFactory {
    public static Autor criarAutor() {
        return Autor.builder()
                .id(1L)
                .nome("Jane Austen")
                .livros(new ArrayList<>())
                .build();
    }

    public static AutorRequest criarAutorRequest() {
        return AutorRequest.builder()
                .id(1L)
                .nome("Jane Austen")
                .build();
    }

    public static AutorResponse criarAutorResponse() {
        return AutorResponse.builder()
                .id(1L)
                .nome("Jane Austen")
                .build();
    }
}
