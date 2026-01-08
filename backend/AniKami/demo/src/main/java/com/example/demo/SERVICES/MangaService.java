package com.example.demo.SERVICES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Mangas;
import com.example.demo.dto.MangaDetalleDTO;
import com.example.demo.helpers.ApiResponse;
import com.example.demo.repository.CapituloRepository;
import com.example.demo.repository.MangaRepository;

@Service
public class MangaService {

    @Autowired
    MangaRepository repository;
    @Autowired
    private CapituloRepository capituloRepository;

    public ApiResponse<List<Mangas>> listarMangas() {

        List<Mangas> manga = repository.findAll();

        return ApiResponse.success("Lista de Mangas", manga);
    }

    public ApiResponse<List<Mangas>> listarMangasPopulares() {

        List<Mangas> mangas = repository.findMangasPopularesAleatorios();

        return ApiResponse.success("Mangas populares", mangas);
    }

    public ApiResponse<List<Mangas>> listarTop10Mangas() {

        Pageable topTen = PageRequest.of(0, 10);
        List<Mangas> mangas = repository.findTopMangasRecientes(topTen);

        return ApiResponse.success("Top 10 mangas", mangas);
    }

    public ApiResponse<List<Mangas>> listarPorGenero(Integer generoId) {

        // 1️⃣ Validar ID
        if (generoId == null || generoId <= 0) {
            return ApiResponse.error("El id del género es inválido");
        }

        // 2️⃣ Consultar
        List<Mangas> mangas = repository.findByGeneroId(generoId);

        // 3️⃣ Validar resultado
        if (mangas.isEmpty()) {
            return ApiResponse.success("No hay mangas para este género", mangas);
        }

        // 4️⃣ Respuesta normal
        return ApiResponse.success("Mangas por género", mangas);
    }

    public ApiResponse<List<Mangas>> listarPorPreferenciasUsuario(Integer usuarioId) {

        if (usuarioId == null || usuarioId <= 0) {
            return ApiResponse.error("El id del usuario es inválido");
        }

        List<Mangas> mangas = repository.findMangasPorPreferenciaUsuario(usuarioId);

        if (mangas.isEmpty()) {
            return ApiResponse.success(
                    "No hay mangas según las preferencias del usuario",
                    mangas);
        }

        return ApiResponse.success(
                "Mangas recomendados según preferencias",
                mangas);
    }

    public ApiResponse<MangaDetalleDTO> obtenerDetalleManga(Integer mangaId) {

        Object[] data = (Object[]) repository.obtenerMangaConAutor(mangaId);

        if (data == null) {
            return ApiResponse.error("Manga no encontrado");
        }

        Long totalCapitulos = capituloRepository.contarCapitulosPorManga(mangaId);

        MangaDetalleDTO dto = new MangaDetalleDTO(
                ((Number) data[0]).intValue(), // mangaId
                (String) data[1], // titulo
                (String) data[2], // descripcion
                (String) data[3], // estado
                (String) data[4], // portada
                ((Number) data[5]).intValue(), // autorId
                (String) data[6], // autorNombre
                (String) data[7], // autorDescripcion
                (String) data[8], // autorFoto
                totalCapitulos);

        return ApiResponse.success("Detalle del manga", dto);
    }

}
