package com.compras.livrariaapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroResponse {
    private Long id;
    private String titulo;
    private String autor;
    private String genero;
}