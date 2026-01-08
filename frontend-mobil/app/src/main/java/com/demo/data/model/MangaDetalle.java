package com.demo.data.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MangaDetalle{

    private int mangaId;
    private String titulo;
    private String descripcion;
    private String estado;
    private String portadaUrl;

    private int autorId;
    private String autorNombre;
    private String autorDescripcion;
    private String autorFoto;

    private int totalCapitulos;

}
