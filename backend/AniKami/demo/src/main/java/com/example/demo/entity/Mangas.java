package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;

@Entity
public class Mangas {
    
    
    int id;
    String titulo;
    String descripcion;
    String estado;
    String portada_url;
    Date  creado_en;


}
