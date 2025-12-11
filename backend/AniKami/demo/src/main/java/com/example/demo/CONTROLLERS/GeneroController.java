package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.helpers.ApiResponse;
import com.example.demo.model.Generos;
import com.example.demo.services.GeneroService;

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

}
