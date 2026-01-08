package com.demo.data.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manga {


    private int id;

    private String titulo;

    private String descripcion;

    private String estado;

    private String portada_url;

    private Date creado_en;
}
