package com.compras.livrariaapi.repository;

import com.compras.livrariaapi.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}