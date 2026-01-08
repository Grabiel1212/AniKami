package com.example.demo.CONTROLLERS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Generos;
import com.example.demo.SERVICES.GeneroService;
import com.example.demo.dto.UsuarioRequest;
import com.example.demo.helpers.ApiResponse;

@RestController
@RequestMapping("/generos")
public class GeneroController {
    @Autowired
    private GeneroService generoService;

    @GetMapping("/listar")
    public ApiResponse<List<Generos>> listarGeneros() {
        List<Generos> lista = generoService.listarGeneros();

        if (lista.isEmpty()) {
            return ApiResponse.error("No hay géneros registrados.", lista);
        }

        return ApiResponse.success("Géneros encontrados.", lista);
    }

    // ❤️ LISTAR PREFERENCIAS DEL USUARIO
    @PostMapping("/preferencias")
    public ResponseEntity<ApiResponse<?>> listarPreferencias(
            @RequestBody UsuarioRequest request) {

        return ResponseEntity.ok(
                generoService.listarPreferencias(request.getUsuarioId()));
    }

}
