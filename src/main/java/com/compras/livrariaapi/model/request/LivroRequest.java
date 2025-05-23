package com.compras.livrariaapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroRequest {
    private Long id;
    private String titulo;
    private Long autorId;
    private Long generoId;
}