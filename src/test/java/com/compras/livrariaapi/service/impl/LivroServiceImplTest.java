package com.compras.livrariaapi.service.impl;

import com.compras.livrariaapi.entity.Autor;
import com.compras.livrariaapi.entity.Genero;
import com.compras.livrariaapi.entity.Livro;
import com.compras.livrariaapi.exception.RecursoNaoEncontradoException;
import com.compras.livrariaapi.factory.AutorFactory;
import com.compras.livrariaapi.factory.GeneroFactory;
import com.compras.livrariaapi.factory.LivroFactory;
import com.compras.livrariaapi.model.request.LivroRequest;
import com.compras.livrariaapi.model.response.LivroResponse;
import com.compras.livrariaapi.repository.AutorRepository;
import com.compras.livrariaapi.repository.GeneroRepository;
import com.compras.livrariaapi.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LivroServiceImplTest {

    @InjectMocks
    private LivroServiceImpl livroService;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private GeneroRepository generoRepository;

    @Mock
    private ModelMapper modelMapper;

    private Livro livro;
    private LivroRequest livroRequest;
    private LivroResponse livroResponse;
    private Autor autor;
    private Genero genero;

    @BeforeEach
    void setUp() {
        livro = LivroFactory.criarLivro();
        livroRequest = LivroFactory.criarLivroRequest();
        livroResponse = LivroFactory.criarLivroResponse();
        autor = AutorFactory.criarAutor();
        genero = GeneroFactory.criarGenero();
    }

    @Test
    void cadastrarLivro() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        when(modelMapper.map(livroRequest, Livro.class)).thenReturn(new Livro());
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(modelMapper.map(livro, LivroResponse.class)).thenReturn(livroResponse);
        LivroResponse response = livroService.cadastrarLivro(livroRequest);
        assertNotNull(response);
        assertEquals(livroResponse.getTitulo(), response.getTitulo());
        assertEquals(livroResponse.getAutor(), response.getAutor());
        assertEquals(livroResponse.getGenero(), response.getGenero());
        verify(modelMapper).map(livroRequest, Livro.class);
        verify(autorRepository).findById(1L);
        verify(generoRepository).findById(1L);
        verify(livroRepository).save(any(Livro.class));
        verify(modelMapper).map(livro, LivroResponse.class);
    }

    @Test
    void listarLivros() {
        var livros = List.of(livro);
        when(livroRepository.findAll()).thenReturn(livros);
        when(modelMapper.map(livro, LivroResponse.class)).thenReturn(livroResponse);

        var response = livroService.listarLivros();

        assertEquals(1, response.size());
        assertEquals(livro.getTitulo(), response.get(0).getTitulo());
    }

    @Test
    void consultarLivroQuandoExiste() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(modelMapper.map(livro, LivroResponse.class)).thenReturn(livroResponse);

        var response = livroService.consultarLivro(1L);

        assertEquals(livro.getTitulo(), response.getTitulo());
    }

    @Test
    void consultarLivroQuandoNaoExiste() {
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> livroService.consultarLivro(1L));
    }

    @Test
    void atualizarLivro() {
        Livro livro = LivroFactory.criarLivro();
        LivroRequest livroRequest = LivroFactory.criarLivroRequest();
        LivroResponse livroResponse = LivroFactory.criarLivroResponse();
        Autor autor = livro.getAutor();
        Genero genero = livro.getGenero();

        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        LivroResponse response = livroService.atualizarLivro(1L, livroRequest);

        assertNotNull(response);
        assertEquals(livro.getTitulo(), response.getTitulo());
        assertEquals(autor.getNome(), response.getAutor());
        assertEquals(genero.getNome(), response.getGenero());

        verify(livroRepository).findById(1L);
        verify(autorRepository).findById(1L);
        verify(generoRepository).findById(1L);
        verify(livroRepository).save(any(Livro.class));
    }

    @Test
    void removerLivro() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(modelMapper.map(livro, LivroResponse.class)).thenReturn(livroResponse);
        when(modelMapper.map(livroResponse, Livro.class)).thenReturn(livro);
        var response = livroService.removerLivro(1L);
        verify(livroRepository).delete(livro);
        assertNotNull(response);
        assertEquals(livro.getTitulo(), response.getTitulo());
    }
}