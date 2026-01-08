package com.example.demo.SERVICES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PaginaDTO;
import com.example.demo.helpers.ApiResponse;
import com.example.demo.repository.PaginaRepository;

@Service
public class PaginaService {

    @Autowired
    private PaginaRepository paginaRepository;

    public ApiResponse<List<PaginaDTO>> obtenerPaginasPorCapitulo(Integer capituloId) {

        List<PaginaDTO> paginas = paginaRepository
                .findPaginasPorCapitulo(capituloId)
                .stream()
                .map(p -> new PaginaDTO(
                        p.getNumeroPagina(),
                        p.getImagenUrl()))
                .toList();

        return ApiResponse.success("Páginas del capítulo", paginas);
    }
}