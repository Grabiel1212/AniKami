package com.example.demo.SERVICES;

import com.example.demo.Model.Mangas;
import java.util.List;

public interface MangaService {

    Mangas crear(Mangas manga);

    List<Mangas> listar();

    Mangas buscarPorId(Integer id);

    Mangas actualizar(Integer id, Mangas nuevo);

    void eliminar(Integer id);

    Mangas cambiarEstado(Integer id, String estado);
}
