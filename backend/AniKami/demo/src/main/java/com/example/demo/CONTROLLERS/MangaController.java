package com.example.demo.CONTROLLERS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Mangas;
import com.example.demo.SERVICES.MangaService;
import com.example.demo.helpers.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mangas")
@RequiredArgsConstructor
public class MangaController {

    @Autowired
    private MangaService mangaService;

    // 📚 LISTAR TODOS LOS MANGAS
    @GetMapping("/listar")
    public ResponseEntity<ApiResponse<List<Mangas>>> listarMangas() {

        ApiResponse<List<Mangas>> response = mangaService.listarMangas();

        return ResponseEntity.ok(response);
    }

    // 🔥 LISTAR 6 MANGAS POPULARES ALEATORIOS
    @GetMapping("/populares")
    public ResponseEntity<ApiResponse<List<Mangas>>> listarMangasPopulares() {

        ApiResponse<List<Mangas>> response = mangaService.listarMangasPopulares();

        return ResponseEntity.ok(response);
    }

}
