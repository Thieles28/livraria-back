package com.compras.livrariaapi.repository;

import com.compras.livrariaapi.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
}