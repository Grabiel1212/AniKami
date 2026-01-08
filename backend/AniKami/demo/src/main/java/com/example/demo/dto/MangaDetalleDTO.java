package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MangaDetalleDTO {

    private Integer mangaId;
    private String titulo;
    private String descripcion;
    private String estado;
    private String portadaUrl;

    private Integer autorId;
    private String autorNombre;
    private String autorDescripcion;
    private String autorFoto;

    private Long totalCapitulos;

}
