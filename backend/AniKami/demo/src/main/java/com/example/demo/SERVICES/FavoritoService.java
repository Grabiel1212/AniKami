package com.example.demo.SERVICES;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Favorito;
import com.example.demo.Model.Mangas;
import com.example.demo.helpers.ApiResponse;
import com.example.demo.repository.FavoritoRepository;
import com.example.demo.repository.MangaRepository;
import com.example.demo.repository.UsuarioRepository;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MangaRepository mangaRepository;

    // ⭐ AGREGAR
    public ApiResponse<?> agregarFavorito(Integer usuarioId, Integer mangaId) {

        // 1️⃣ validar usuario
        if (!usuarioRepository.existsById(usuarioId)) {
            return ApiResponse.error("El usuario no existe");
        }

        // 2️⃣ validar manga
        if (!mangaRepository.existsById(mangaId)) {
            return ApiResponse.error("El manga no existe");
        }

        // 3️⃣ validar duplicado
        if (favoritoRepository
                .existsByUsuario_IdUsuarioAndManga_Id(usuarioId, mangaId)) {
            return ApiResponse.error("El manga ya está en favoritos");
        }

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuarioRepository.findById(usuarioId).get());
        favorito.setManga(mangaRepository.findById(mangaId).get());
        favorito.setCreadoEn(LocalDateTime.now());

        favoritoRepository.save(favorito);

        return ApiResponse.success("Manga agregado a favoritos", null);
    }

    // 📋 LISTAR
    public ApiResponse<List<Mangas>> listarFavoritos(Integer usuarioId) {

        // validar usuario
        if (!usuarioRepository.existsById(usuarioId)) {
            return ApiResponse.error("El usuario no existe");
        }

        return ApiResponse.success(
                "Lista de favoritos",
                favoritoRepository.findMangasFavoritos(usuarioId));
    }

    // ❌ ELIMINAR FAVORITO
    public ApiResponse<?> eliminarFavorito(Integer usuarioId, Integer mangaId) {

        // 🔹 validar solo usuario
        if (!usuarioRepository.existsById(usuarioId)) {
            return ApiResponse.error("El usuario no existe");
        }

        // 🔹 buscar favorito primero (SIN lanzar excepción)
        return favoritoRepository
                .findByUsuario_IdUsuarioAndManga_Id(usuarioId, mangaId)
                .map(favorito -> {
                    favoritoRepository.delete(favorito);
                    return ApiResponse.success("Manga eliminado de favoritos", null);
                })
                .orElseGet(() -> ApiResponse.success("El manga no estaba en favoritos", null));
    }

}
