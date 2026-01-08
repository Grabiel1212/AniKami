package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Model.Capitulos;

public interface CapituloRepository extends JpaRepository<Capitulos, Integer> {
    @Query(value = "SELECT COUNT(*) FROM capitulos WHERE manga_id = :mangaId", nativeQuery = true)
    Long contarCapitulosPorManga(@Param("mangaId") Integer mangaId);

    @Query(value = """
                SELECT *
                FROM capitulos
                WHERE manga_id = :mangaId
                  AND numero BETWEEN :inicio AND :fin
                ORDER BY numero ASC
            """, nativeQuery = true)
    List<Capitulos> findCapitulosPorRango(
            @Param("mangaId") Integer mangaId,
            @Param("inicio") Integer inicio,
            @Param("fin") Integer fin);

}
