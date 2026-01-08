package com.example.demo.SERVICES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Capitulos;
import com.example.demo.dto.RangoCapitulosRequest;
import com.example.demo.helpers.ApiResponse;
import com.example.demo.repository.CapituloRepository;

@Service
public class CapituloService {

    @Autowired
    private CapituloRepository capituloRepository;

    public ApiResponse<List<Capitulos>> listarPorRango(RangoCapitulosRequest req) {

        List<Capitulos> capitulos = capituloRepository.findCapitulosPorRango(
                req.getMangaId(),
                req.getInicio(),
                req.getFin());

        return ApiResponse.success("Capítulos por rango", capitulos);
    }
}
