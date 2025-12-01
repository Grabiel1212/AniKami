package com.example.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "paginas")
public class Paginas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "capitulo_id")
    int capitulo_id;
    @Column(name = "numero_pagina")
    int numero_pagina;
    @Column(name = "imagen_url")
    String imagen_url;
}
