package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Model.Pagina;

public interface PaginaRepository extends JpaRepository<Pagina, Integer> {

    @Query("""
                SELECT p
                FROM Pagina p
                WHERE p.capitulo.id = :capituloId
                ORDER BY p.numeroPagina ASC
            """)
    List<Pagina> findPaginasPorCapitulo(@Param("capituloId") Integer capituloId);

}
