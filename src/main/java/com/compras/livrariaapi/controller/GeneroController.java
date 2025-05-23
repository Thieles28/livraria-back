package com.compras.livrariaapi.controller;

import com.compras.livrariaapi.model.request.GeneroRequest;
import com.compras.livrariaapi.model.response.GeneroResponse;
import com.compras.livrariaapi.service.GeneroService;
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
@RequestMapping("generos")
@RequiredArgsConstructor
@Tag(name = "Gênero", description = "Operações relacionadas aos gêneros literários")
public class GeneroController {

    private final GeneroService generoService;

    @PostMapping("cadastrar")
    @Operation(summary = "Cadastrar Genero")
    @ApiResponse(responseCode = "201", description = "Cadastrar Genero",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneroResponse.class)))
    public ResponseEntity<GeneroResponse> cadastrarGenero(@RequestBody @Valid GeneroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generoService.cadastrarGenero(request));
    }

    @GetMapping
    @Operation(summary = "Listar Gênero")
    @ApiResponse(responseCode = "200", description = "Listar Gênero",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneroResponse.class)))
    public ResponseEntity<List<GeneroResponse>> listarGenero() {
        return ResponseEntity.status(HttpStatus.OK).body(generoService.listarGenero());
    }

    @GetMapping("{id}")
    @Operation(summary = "Consultar Gênero")
    @ApiResponse(responseCode = "200", description = "Consultar Gênero",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneroResponse.class)))
    public ResponseEntity<GeneroResponse> consultarGenero(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(generoService.consultarGenero(id));
    }

    @PutMapping("atualizar/{id}")
    @Operation(summary = "Atualizar Gênero")
    @ApiResponse(responseCode = "201", description = "Atualizar Gênero",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneroResponse.class)))
    public ResponseEntity<GeneroResponse> atualizarGenero(@PathVariable("id") Long id, @RequestBody @Valid GeneroRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(generoService.atualizarGenero(id, request));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Remover Gênero")
    @ApiResponse(responseCode = "201", description = "Remover Gênero",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneroResponse.class)))
    public ResponseEntity<GeneroResponse> removerGenero(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(generoService.removerGenero(id));
    }
}