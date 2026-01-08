package com.example.demo.CONTROLLERS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.SERVICES.FavoritoService;
import com.example.demo.dto.FavoritoRequestDTO;
import com.example.demo.helpers.ApiResponse;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    // ⭐ AGREGAR FAVORITO
    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse<?>> agregarFavorito(
            @RequestBody FavoritoRequestDTO request) {

        return ResponseEntity.ok(
                favoritoService.agregarFavorito(
                        request.getUsuarioId(),
                        request.getMangaId()));
    }

    // 📋 LISTAR FAVORITOS DEL USUARIO
    @PostMapping("/listar")
    public ResponseEntity<ApiResponse<?>> listarFavoritos(
            @RequestBody FavoritoRequestDTO request) {

        return ResponseEntity.ok(
                favoritoService.listarFavoritos(
                        request.getUsuarioId()));
    }

    // ❌ ELIMINAR FAVORITO
    @DeleteMapping("/eliminar")
    public ResponseEntity<ApiResponse<?>> eliminarFavorito(
            @RequestBody FavoritoRequestDTO request) {

        return ResponseEntity.ok(
                favoritoService.eliminarFavorito(
                        request.getUsuarioId(),
                        request.getMangaId()));
    }
}
