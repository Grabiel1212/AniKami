package com.example.demo.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "capitulos")
public class Capitulos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "manga_id")
    int manga_id;
    @Column(name = "numero")
    int numero;
    @Column(name = "titulo")
    String imagen_url;
    @Column(name = "publicado_en")
    Date publicado_en;

}
