package com.example.demo.CONTROLLERS;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Mangas;
import com.example.demo.SERVICES.MangaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mangas")
@RequiredArgsConstructor
public class MangaController {
    

    private final MangaService mangaService;

    @PostMapping
    public Mangas crear(@RequestBody Mangas manga){
        return mangaService.crear(manga);
    }

    @GetMapping
    public List<Mangas> listar(){
        return mangaService.listar();
    }

    @PutMapping("/{id}")
    public Mangas actualizar(@PathVariable Integer id, @RequestBody Mangas manga){
        return mangaService.actualizar(id, manga);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        mangaService.eliminar(id);
    }

    @PutMapping("/{id}/estado")
    public Mangas cambiarEstado(@PathVariable Integer id, @RequestParam String estado){
        return mangaService.cambiarEstado(id, estado.toUpperCase());
    }
}
