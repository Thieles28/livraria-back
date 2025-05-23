package com.compras.livrariaapi.repository;

import com.compras.livrariaapi.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}