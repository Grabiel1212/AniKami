package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Mangas;

public interface MangaRepository extends JpaRepository<Mangas, Integer> {
    List<Mangas> findByEstado(String estado); // Para habilitar/deshabilitar vistas
}
