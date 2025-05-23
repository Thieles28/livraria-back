package com.compras.livrariaapi.factory;

import com.compras.livrariaapi.entity.Genero;
import com.compras.livrariaapi.model.request.GeneroRequest;
import com.compras.livrariaapi.model.response.GeneroResponse;

import java.util.ArrayList;

public class GeneroFactory {
    public static Genero criarGenero() {
        return Genero.builder()
                .id(1L)
                .nome("Jane Austen")
                .livros(new ArrayList<>())
                .build();
    }

    public static GeneroRequest criarGeneroRequest() {
        return GeneroRequest.builder()
                .id(1L)
                .nome("Jane Austen")
                .build();
    }

    public static GeneroResponse criarGeneroResponse() {
        return GeneroResponse.builder()
                .id(1L)
                .nome("Jane Austen")
                .build();
    }
}
