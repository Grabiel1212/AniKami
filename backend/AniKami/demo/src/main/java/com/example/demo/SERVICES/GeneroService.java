package com.example.demo.SERVICES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Generos;
import com.example.demo.helpers.ApiResponse;
import com.example.demo.repository.GeneroRepository;
import com.example.demo.repository.UsuarioRepository;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 📋 LISTAR TODOS LOS GÉNEROS
    public List<Generos> listarGeneros() {
        return generoRepository.findAll();
    }

    // ❤️ LISTAR PREFERENCIAS DEL USUARIO
    public ApiResponse<?> listarPreferencias(Integer usuarioId) {

        if (!usuarioRepository.existsById(usuarioId)) {
            return ApiResponse.error("El usuario no existe");
        }

        return ApiResponse.success(
                "Preferencias del usuario",
                generoRepository.findGenerosPreferidos(usuarioId));
    }

}