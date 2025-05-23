package com.compras.livrariaapi.controller;

import com.compras.livrariaapi.model.request.AutorRequest;
import com.compras.livrariaapi.model.response.AutorResponse;
import com.compras.livrariaapi.service.AutorService;
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
@RequestMapping("autores")
@RequiredArgsConstructor
@Tag(name = "Autor", description = "Operações relacionadas aos autores")
public class AutorController {

    private final AutorService autorService;

    @PostMapping("cadastrar")
    @Operation(summary = "Cadastrar Autor")
    @ApiResponse(responseCode = "201", description = "Cadastrar Autor",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class)))
    public ResponseEntity<AutorResponse> cadastrarAutor(@RequestBody @Valid AutorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.cadastrarAutor(request));
    }

    @GetMapping
    @Operation(summary = "Listar Autor")
    @ApiResponse(responseCode = "200", description = "Listar Autor",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class)))
    public ResponseEntity<List<AutorResponse>> listarAutor() {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.listarAutor());
    }

    @GetMapping("{id}")
    @Operation(summary = "Consultar Autor")
    @ApiResponse(responseCode = "200", description = "Consultar Autor",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class)))
    public ResponseEntity<AutorResponse> consultarAutor(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.consultarAutor(id));
    }

    @PutMapping("atualizar/{id}")
    @Operation(summary = "Atualizar Autor")
    @ApiResponse(responseCode = "201", description = "Atualizar Autor",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class)))
    public ResponseEntity<AutorResponse> atualizarAutor(@PathVariable("id") Long id, @RequestBody @Valid AutorRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.atualizarAutor(id, request));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Remover Autor")
    @ApiResponse(responseCode = "201", description = "Remover Autor",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class)))
    public ResponseEntity<AutorResponse> removerAutor(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.removerAutor(id));
    }
}