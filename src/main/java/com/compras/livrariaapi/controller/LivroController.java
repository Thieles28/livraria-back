package com.compras.livrariaapi.controller;

import com.compras.livrariaapi.model.request.LivroRequest;
import com.compras.livrariaapi.model.response.LivroResponse;
import com.compras.livrariaapi.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
@Tag(name = "Livro", description = "Operações relacionadas a livros")
public class LivroController {

    private final LivroService livroService;

    @PostMapping("cadastrar")
    @Operation(summary = "Cadastrar livro")
    @ApiResponse(responseCode = "201", description = "Cadastrar livro",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class)))
    public ResponseEntity<LivroResponse> cadastrarLivro(@RequestBody @Valid LivroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.cadastrarLivro(request));
    }

    @GetMapping
    @Operation(summary = "Listar livro")
    @ApiResponse(responseCode = "200", description = "Listar livro",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class)))
    public ResponseEntity<List<LivroResponse>> listarLivro() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.listarLivros());
    }

    @GetMapping("{id}")
    @Operation(summary = "Consultar Livro")
    @ApiResponse(responseCode = "200", description = "Consultar Livro",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class)))
    public ResponseEntity<LivroResponse> consultarLivro(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.consultarLivro(id));
    }

    @PutMapping("atualizar/{id}")
    @Operation(summary = "Atualizar livro")
    @ApiResponse(responseCode = "201", description = "Atualizar livro",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class)))
    public ResponseEntity<LivroResponse> atualizarLivro(@PathVariable("id") Long id, @RequestBody @Valid LivroRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.atualizarLivro(id, request));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Remover livro")
    @ApiResponse(responseCode = "201", description = "Remover livro",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class)))
    public ResponseEntity<LivroResponse> removerLivro(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.removerLivro(id));
    }
}