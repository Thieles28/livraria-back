package com.compras.livrariaapi.factory;

import com.compras.livrariaapi.entity.Autor;
import com.compras.livrariaapi.entity.Genero;
import com.compras.livrariaapi.entity.Livro;
import com.compras.livrariaapi.model.request.LivroRequest;
import com.compras.livrariaapi.model.response.LivroResponse;

import java.util.ArrayList;

public class LivroFactory {
    public static Livro criarLivro() {
        return Livro.builder()
                .id(1L)
                .titulo("Orgulho e Preconceito")
                .autor(Autor.builder()
                        .id(1L)
                        .nome("Jane Austen")
                        .livros(new ArrayList<>())
                        .build())
                .genero(new Genero())
                .build();
    }

    public static LivroRequest criarLivroRequest() {
        return LivroRequest.builder()
                .id(1L)
                .titulo("Orgulho e Preconceito")
                .autorId(1L)
                .generoId(1L)
                .build();
    }

    public static LivroResponse criarLivroResponse() {
        return LivroResponse.builder()
                .id(1L)
                .titulo("Orgulho e Preconceito")
                .autor("Oscar Wilde")
                .genero("Terror")
                .build();
    }
}
