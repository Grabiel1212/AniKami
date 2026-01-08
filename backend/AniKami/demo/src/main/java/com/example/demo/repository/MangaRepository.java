package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Model.Mangas;

public interface MangaRepository extends JpaRepository<Mangas, Integer> {

        @Query(value = """
                        SELECT DISTINCT m.*
                        FROM mangas m
                        JOIN manga_genero mg ON m.id = mg.manga_id
                        WHERE mg.genero_id = :generoId
                        """, nativeQuery = true)
        List<Mangas> findByGeneroId(@Param("generoId") Integer generoId);

        @Query(value = "SELECT * FROM mangas ORDER BY RAND() LIMIT 15", nativeQuery = true)
        List<Mangas> findMangasPopularesAleatorios();

        @Query("SELECT m FROM Mangas m ORDER BY m.creadoEn DESC")
        List<Mangas> findTopMangasRecientes(Pageable pageable);

        @Query(value = """
                        SELECT DISTINCT m.*
                        FROM mangas m
                        JOIN manga_genero mg ON m.id = mg.manga_id
                        JOIN usuario_preferencia_genero upg ON mg.genero_id = upg.genero_id
                        WHERE upg.usuario_id = :usuarioId
                        """, nativeQuery = true)
        List<Mangas> findMangasPorPreferenciaUsuario(
                        @Param("usuarioId") Integer usuarioId);

        @Query(value = """
                            SELECT
                                m.id AS mangaId,
                                m.titulo AS titulo,
                                m.descripcion AS descripcion,
                                m.estado AS estado,
                                m.portada_url AS portadaUrl,
                                a.id AS autorId,
                                a.nombre AS autorNombre,
                                a.descripcion AS autorDescripcion,
                                a.foto AS autorFoto
                            FROM mangas m
                            INNER JOIN manga_autor ma ON m.id = ma.manga_id
                            INNER JOIN autores a ON a.id = ma.autor_id
                            WHERE m.id = :mangaId
                        """, nativeQuery = true)
        Object obtenerMangaConAutor(@Param("mangaId") Integer mangaId);

}
