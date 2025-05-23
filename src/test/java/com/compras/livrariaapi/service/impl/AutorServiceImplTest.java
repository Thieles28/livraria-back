package com.compras.livrariaapi.service.impl;

import com.compras.livrariaapi.entity.Autor;
import com.compras.livrariaapi.exception.RecursoNaoEncontradoException;
import com.compras.livrariaapi.factory.AutorFactory;
import com.compras.livrariaapi.model.request.AutorRequest;
import com.compras.livrariaapi.model.response.AutorResponse;
import com.compras.livrariaapi.repository.AutorRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutorServiceImplTest {
    @InjectMocks
    private AutorServiceImpl autorService;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private ModelMapper modelMapper;

    private Autor autor;
    private AutorRequest autorRequest;
    private AutorResponse autorResponse;

    @BeforeEach
    void setUp() {
        autor = AutorFactory.criarAutor();
        autorRequest = AutorFactory.criarAutorRequest();
        autorResponse = AutorFactory.criarAutorResponse();
    }

    @Test
    void cadastrarAutor() {
        when(modelMapper.map(autorRequest, Autor.class)).thenReturn(autor);
        when(autorRepository.save(autor)).thenReturn(autor);
        when(modelMapper.map(autor, AutorResponse.class)).thenReturn(autorResponse);

        var response = autorService.cadastrarAutor(autorRequest);

        assertEquals(1L, response.getId());
        assertEquals(autor.getNome(), response.getNome());
        verify(autorRepository).save(autor);
    }

    @Test
    void listarAutor() {
        var autores = List.of(autor);
        when(autorRepository.findAll()).thenReturn(autores);
        when(modelMapper.map(autor, AutorResponse.class)).thenReturn(autorResponse);

        var response = autorService.listarAutor();

        assertEquals(1, response.size());
        assertEquals(autor.getNome(), response.get(0).getNome());
    }

    @Test
    void consultarAutor_quandoExiste() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(modelMapper.map(autor, AutorResponse.class)).thenReturn(autorResponse);

        var response = autorService.consultarAutor(1L);

        assertEquals(autor.getNome(), response.getNome());
    }

    @Test
    void consultarAutor_quandoNaoExiste() {
        when(autorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> autorService.consultarAutor(1L));
    }

    @Test
    void atualizarAutor() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        doNothing().when(modelMapper).map(autorRequest, autor);
        when(modelMapper.map(any(Autor.class), eq(Autor.class))).thenReturn(autor);
        when(autorRepository.save(autor)).thenReturn(autor);
        when(modelMapper.map(autor, AutorResponse.class)).thenReturn(autorResponse);
        var response = autorService.atualizarAutor(1L, autorRequest);
        assertNotNull(response);
        assertEquals(autor.getNome(), response.getNome());
        verify(autorRepository).findById(1L);
        verify(modelMapper).map(autorRequest, autor);
        verify(modelMapper).map(autor, AutorResponse.class);
        verify(autorRepository).save(autor);
    }

    @Test
    void removerAutor() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(modelMapper.map(autor, AutorResponse.class)).thenReturn(autorResponse);
        when(modelMapper.map(autorResponse, Autor.class)).thenReturn(autor);
        var response = autorService.removerAutor(1L);
        verify(autorRepository).delete(autor);
        assertNotNull(response);
        assertEquals(autor.getNome(), response.getNome());
    }
}