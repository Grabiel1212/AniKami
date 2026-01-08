package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Model.Favorito;
import com.example.demo.Model.Mangas;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {

    // ✔️ Verificar si existe
    boolean existsByUsuario_IdUsuarioAndManga_Id(Integer usuarioId, Integer mangaId);

    // ✔️ Buscar favorito (para eliminar seguro)
    Optional<Favorito> findByUsuario_IdUsuarioAndManga_Id(Integer usuarioId, Integer mangaId);

    // ✔️ Listar mangas favoritos del usuario
    @Query("""
                SELECT f.manga
                FROM Favorito f
                WHERE f.usuario.idUsuario = :usuarioId
                ORDER BY f.id DESC
            """)
    List<Mangas> findMangasFavoritos(@Param("usuarioId") Integer usuarioId);
}
