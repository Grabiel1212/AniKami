package com.example.demo.SERVICES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Mangas;
import com.example.demo.helpers.ApiResponse;
import com.example.demo.repository.MangaRepository;

@Service
public class MangaService {

    @Autowired
    MangaRepository repository;

    public ApiResponse<List<Mangas>> listarMangas() {

        List<Mangas> manga = repository.findAll();

        return ApiResponse.success("Lista de Mangas", manga);
    }

    public ApiResponse<List<Mangas>> listarMangasPopulares() {

        List<Mangas> mangas = repository.findMangasPopularesAleatorios();

        return ApiResponse.success("Mangas populares", mangas);
    }

}
