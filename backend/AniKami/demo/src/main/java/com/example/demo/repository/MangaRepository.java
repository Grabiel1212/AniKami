package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.Mangas;

public interface MangaRepository extends JpaRepository<Mangas, Integer> {

    @Query(value = "SELECT * FROM mangas ORDER BY RAND() LIMIT 6", nativeQuery = true)
    List<Mangas> findMangasPopularesAleatorios();
}
