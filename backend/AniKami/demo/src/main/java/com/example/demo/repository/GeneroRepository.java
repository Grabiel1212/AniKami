package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.Generos;

public interface GeneroRepository extends JpaRepository<Generos, Integer> {

    @Query("""
                SELECT g
                FROM Generos g
                WHERE g.id IN (
                    SELECT upg.generoId
                    FROM UsuarioPreferenciaGenero upg
                    WHERE upg.usuarioId = :usuarioId
                )
            """)
    List<Generos> findGenerosPreferidos(Integer usuarioId);
}
