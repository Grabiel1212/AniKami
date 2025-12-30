package com.example.demo.ServicesImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Model.Mangas;
import com.example.demo.SERVICES.MangaService;
import com.example.demo.repository.MangaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MangaServiceImpl implements MangaService {

    private final MangaRepository mangaRepository;

    @Override
    public Mangas crear(Mangas manga) {
        manga.setEstado("HABILITADO"); // Valor por defecto
        return mangaRepository.save(manga);
    }

    @Override
    public List<Mangas> listar() {
        return mangaRepository.findAll();
    }

    @Override
    public Mangas buscarPorId(Integer id) {
        return mangaRepository.findById(id).orElse(null);
    }

    @Override
    public Mangas actualizar(Integer id, Mangas nuevo) {
        Mangas manga = buscarPorId(id);
        if (manga == null)
            return null;

        manga.setTitulo(nuevo.getTitulo());
        manga.setDescripcion(nuevo.getDescripcion());
        manga.setPortada_url(nuevo.getPortada_url());
        manga.setEstado(nuevo.getEstado());

        return mangaRepository.save(manga);
    }

    @Override
    public void eliminar(Integer id) {
        mangaRepository.deleteById(id);
    }

    @Override
    public Mangas cambiarEstado(Integer id, String estado) {
        Mangas manga = buscarPorId(id);
        if (manga == null)
            return null;

        manga.setEstado(estado); // HABILITADO / DESHABILITADO
        return mangaRepository.save(manga);
    }
}