package com.compras.livrariaapi.service.impl;

import com.compras.livrariaapi.entity.Genero;
import com.compras.livrariaapi.exception.RecursoNaoEncontradoException;
import com.compras.livrariaapi.factory.GeneroFactory;
import com.compras.livrariaapi.model.request.GeneroRequest;
import com.compras.livrariaapi.model.response.GeneroResponse;
import com.compras.livrariaapi.repository.GeneroRepository;
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
class GeneroServiceImplTest {

    @InjectMocks
    private GeneroServiceImpl generoService;

    @Mock
    private GeneroRepository generoRepository;

    @Mock
    private ModelMapper modelMapper;

    private Genero genero;
    private GeneroRequest generoRequest;
    private GeneroResponse generoResponse;

    @BeforeEach
    void setUp() {
        genero = GeneroFactory.criarGenero();
        generoRequest = GeneroFactory.criarGeneroRequest();
        generoResponse = GeneroFactory.criarGeneroResponse();
    }

    @Test
    void cadastrarGenero() {
        when(modelMapper.map(generoRequest, Genero.class)).thenReturn(genero);
        when(generoRepository.save(genero)).thenReturn(genero);
        when(modelMapper.map(genero, GeneroResponse.class)).thenReturn(generoResponse);

        var response = generoService.cadastrarGenero(generoRequest);

        assertEquals(1L, response.getId());
        assertEquals(genero.getNome(), response.getNome());
        verify(generoRepository).save(genero);
    }

    @Test
    void listarGenero() {
        var generos = List.of(genero);
        when(generoRepository.findAll()).thenReturn(generos);
        when(modelMapper.map(genero, GeneroResponse.class)).thenReturn(generoResponse);

        var response = generoService.listarGenero();

        assertEquals(1, response.size());
        assertEquals(genero.getNome(), response.get(0).getNome());
    }

    @Test
    void consultarGeneroQuandoExiste() {
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        when(modelMapper.map(genero, GeneroResponse.class)).thenReturn(generoResponse);

        var response = generoService.consultarGenero(1L);

        assertEquals(genero.getNome(), response.getNome());
    }

    @Test
    void consultarGeneroQuandoNaoExiste() {
        when(generoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> generoService.consultarGenero(1L));
    }

    @Test
    void atualizarGenero() {
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        doNothing().when(modelMapper).map(generoRequest, genero);
        when(modelMapper.map(any(Genero.class), eq(Genero.class))).thenReturn(genero);
        when(generoRepository.save(genero)).thenReturn(genero);
        when(modelMapper.map(genero, GeneroResponse.class)).thenReturn(generoResponse);
        var response = generoService.atualizarGenero(1L, generoRequest);
        assertNotNull(response);
        assertEquals(genero.getNome(), response.getNome());
        verify(generoRepository).findById(1L);
        verify(modelMapper).map(generoRequest, genero);
        verify(modelMapper).map(genero, GeneroResponse.class);
        verify(generoRepository).save(genero);
    }

    @Test
    void removerGenero() {
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        when(modelMapper.map(genero, GeneroResponse.class)).thenReturn(generoResponse);
        when(modelMapper.map(generoResponse, Genero.class)).thenReturn(genero);
        var response = generoService.removerGenero(1L);
        verify(generoRepository).delete(genero);
        assertNotNull(response);
        assertEquals(genero.getNome(), response.getNome());
    }
}