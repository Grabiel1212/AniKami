package com.example.demo.CONTROLLERS;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Capitulos;
import com.example.demo.Model.Mangas;
import com.example.demo.SERVICES.CapituloService;
import com.example.demo.SERVICES.MangaService;
import com.example.demo.SERVICES.PaginaService;
import com.example.demo.dto.MangaDetalleDTO;
import com.example.demo.dto.PaginaDTO;
import com.example.demo.dto.RangoCapitulosRequest;
import com.example.demo.dto.UsuarioRequest;
import com.example.demo.helpers.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mangas")
@RequiredArgsConstructor
public class MangaController {

    @Autowired
    private MangaService mangaService;

    @Autowired
    private CapituloService capituloService;

    @Autowired
    private PaginaService paginaService;

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

    @GetMapping("/top10")
    public ResponseEntity<ApiResponse<List<Mangas>>> listarMangasTop10() {

        ApiResponse<List<Mangas>> response = mangaService.listarTop10Mangas();

        return ResponseEntity.ok(response);
    }

    // listar por genero
    @PostMapping("/por-genero")
    public ResponseEntity<ApiResponse<List<Mangas>>> obtenerMangasPorGenero(
            @RequestBody Map<String, Integer> body) {

        Integer generoId = body.get("generoId");

        ApiResponse<List<Mangas>> response = mangaService.listarPorGenero(generoId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/recomendados")
    public ResponseEntity<ApiResponse<List<Mangas>>> mangasRecomendados(
            @RequestBody UsuarioRequest request) {

        return ResponseEntity.ok(
                mangaService.listarPorPreferenciasUsuario(
                        request.getUsuarioId()));
    }

    @PostMapping("/detalle")
    public ResponseEntity<ApiResponse<MangaDetalleDTO>> obtenerDetalle(
            @RequestBody MangaDetalleDTO request) {

        return ResponseEntity.ok(
                mangaService.obtenerDetalleManga(request.getMangaId()));
    }

    @PostMapping("/capitulos/por-rango")
    public ResponseEntity<ApiResponse<List<Capitulos>>> obtenerCapitulosPorRango(
            @RequestBody RangoCapitulosRequest request) {

        return ResponseEntity.ok(
                capituloService.listarPorRango(request));
    }

    @PostMapping("/paginas/por-capitulo")
    public ResponseEntity<ApiResponse<List<PaginaDTO>>> obtenerPaginas(
            @RequestBody Map<String, Integer> body) {

        Integer capituloId = body.get("capituloId");

        return ResponseEntity.ok(
                paginaService.obtenerPaginasPorCapitulo(capituloId));
    }

}
