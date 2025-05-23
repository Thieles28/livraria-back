package com.compras.livrariaapi.service.impl;

import com.compras.livrariaapi.entity.Genero;
import com.compras.livrariaapi.exception.RecursoNaoEncontradoException;
import com.compras.livrariaapi.model.request.GeneroRequest;
import com.compras.livrariaapi.model.response.GeneroResponse;
import com.compras.livrariaapi.repository.GeneroRepository;
import com.compras.livrariaapi.service.GeneroService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.compras.livrariaapi.util.MensagensErro.GENERO_NAO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class GeneroServiceImpl implements GeneroService {

    private final GeneroRepository generoRepository;
    private final ModelMapper modelMapper;

    @Override
    public GeneroResponse cadastrarGenero(GeneroRequest request) {
        var genero = modelMapper.map(request, Genero.class);
        return modelMapper.map(generoRepository.save(genero), GeneroResponse.class);
    }

    @Override
    public List<GeneroResponse> listarGenero() {
        return generoRepository.findAll().stream()
                .map(genero -> modelMapper.map(genero, GeneroResponse.class)
                )
                .toList();
    }

    @Override
    public GeneroResponse consultarGenero(Long id) {
        return generoRepository.findById(id)
                .map(genero -> modelMapper.map(genero, GeneroResponse.class)
                ).orElseThrow(() -> new RecursoNaoEncontradoException(GENERO_NAO_ENCONTRADO));
    }

    @Override
    public GeneroResponse atualizarGenero(Long id, GeneroRequest request) {
        var genero = obterGenero(id);
        modelMapper.map(request, genero);
        return modelMapper.map(generoRepository.save(modelMapper.map(genero, Genero.class)), GeneroResponse.class);
    }

    @Override
    public GeneroResponse removerGenero(Long id) {
        var genero = consultarGenero(id);
        var removerGenero = modelMapper.map(genero, Genero.class);
        generoRepository.delete(removerGenero);
        return genero;
    }

    private Genero obterGenero(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(GENERO_NAO_ENCONTRADO));
    }
}