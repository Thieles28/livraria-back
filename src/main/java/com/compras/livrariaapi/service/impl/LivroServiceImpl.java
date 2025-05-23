package com.compras.livrariaapi.service.impl;

import com.compras.livrariaapi.entity.Autor;
import com.compras.livrariaapi.entity.Genero;
import com.compras.livrariaapi.entity.Livro;
import com.compras.livrariaapi.exception.RecursoNaoEncontradoException;
import com.compras.livrariaapi.model.request.LivroRequest;
import com.compras.livrariaapi.model.response.LivroResponse;
import com.compras.livrariaapi.repository.AutorRepository;
import com.compras.livrariaapi.repository.GeneroRepository;
import com.compras.livrariaapi.repository.LivroRepository;
import com.compras.livrariaapi.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.compras.livrariaapi.util.MensagensErro.AUTOR_NAO_ENCONTRADO;
import static com.compras.livrariaapi.util.MensagensErro.GENERO_NAO_ENCONTRADO;
import static com.compras.livrariaapi.util.MensagensErro.LIVRO_NAO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final GeneroRepository generoRepository;
    private final ModelMapper modelMapper;

    @Override
    public LivroResponse cadastrarLivro(LivroRequest request) {
        var livro = modelMapper.map(request, Livro.class);
        livro.setAutor(obterAutor(request));
        livro.setGenero(obterGenero(request));
        return mapearLivroSalvoResposta(livro);
    }

    @Override
    public List<LivroResponse> listarLivros() {
        return livroRepository.findAll().stream()
                .map(this::mapearLivroResposta
                )
                .toList();
    }

    @Override
    public LivroResponse consultarLivro(Long id) {
        return livroRepository.findById(id)
                .map(this::mapearLivroResposta
                )
                .orElseThrow(() -> new RecursoNaoEncontradoException(LIVRO_NAO_ENCONTRADO));
    }

    @Override
    public LivroResponse atualizarLivro(Long id, LivroRequest request) {
        Livro livro = obterLivro(id);
        atualizarDadosLivro(livro, request);
        Livro livroSalvo = livroRepository.save(livro);
        return mapearParaResponse(livroSalvo);
    }

    private void atualizarDadosLivro(Livro livro, LivroRequest request) {
        livro.setTitulo(request.getTitulo());
        livro.setAutor(obterAutor(request));
        livro.setGenero(obterGenero(request));
    }

    private LivroResponse mapearParaResponse(Livro livro) {
        return LivroResponse.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .autor(livro.getAutor().getNome())
                .genero(livro.getGenero().getNome())
                .build();
    }

    @Override
    public LivroResponse removerLivro(Long id) {
        var livro = consultarLivro(id);
        var removerLivro = modelMapper.map(livro, Livro.class);
        livroRepository.delete(removerLivro);
        return livro;
    }

    private Genero obterGenero(LivroRequest request) {
        return generoRepository.findById(request.getGeneroId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(GENERO_NAO_ENCONTRADO));
    }

    private Autor obterAutor(LivroRequest request) {
        return autorRepository.findById(request.getAutorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(AUTOR_NAO_ENCONTRADO));
    }

    private Livro obterLivro(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(LIVRO_NAO_ENCONTRADO));
    }

    private LivroResponse mapearLivroResposta(Livro produto) {
        LivroResponse livro = modelMapper.map(produto, LivroResponse.class);
        livro.setAutor(produto.getAutor().getNome());
        livro.setGenero(produto.getGenero().getNome());
        return livro;
    }

    private LivroResponse mapearLivroSalvoResposta(Livro livro) {
        var response = modelMapper.map(livroRepository.save(livro), LivroResponse.class);
        response.setAutor(livro.getAutor().getNome());
        response.setGenero(livro.getGenero().getNome());
        return response;
    }
}