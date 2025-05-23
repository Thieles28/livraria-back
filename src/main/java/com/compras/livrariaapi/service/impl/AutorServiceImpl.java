package com.compras.livrariaapi.service.impl;

import com.compras.livrariaapi.entity.Autor;
import com.compras.livrariaapi.exception.RecursoNaoEncontradoException;
import com.compras.livrariaapi.model.request.AutorRequest;
import com.compras.livrariaapi.model.response.AutorResponse;
import com.compras.livrariaapi.repository.AutorRepository;
import com.compras.livrariaapi.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.compras.livrariaapi.util.MensagensErro.AUTOR_NAO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;
    private final ModelMapper modelMapper;

    @Override
    public AutorResponse cadastrarAutor(AutorRequest request) {
        var autor = modelMapper.map(request, Autor.class);
        return modelMapper.map(autorRepository.save(autor), AutorResponse.class);
    }

    @Override
    public List<AutorResponse> listarAutor() {
        return autorRepository.findAll().stream()
                .map(autor -> modelMapper.map(autor, AutorResponse.class)
                )
                .toList();
    }

    @Override
    public AutorResponse consultarAutor(Long id) {
        return autorRepository.findById(id)
                .map(autor -> modelMapper.map(autor, AutorResponse.class)
                ).orElseThrow(() -> new RecursoNaoEncontradoException(AUTOR_NAO_ENCONTRADO));
    }

    @Override
    public AutorResponse atualizarAutor(Long id, AutorRequest request) {
        var autor = obterAutor(id);
        modelMapper.map(request, autor);
        return modelMapper.map(autorRepository.save(modelMapper.map(autor, Autor.class)), AutorResponse.class);
    }

    @Override
    public AutorResponse removerAutor(Long id) {
        var autor = consultarAutor(id);
        var removerAutor = modelMapper.map(autor, Autor.class);
        autorRepository.delete(removerAutor);
        return autor;
    }

    private Autor obterAutor(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(AUTOR_NAO_ENCONTRADO));
    }
}