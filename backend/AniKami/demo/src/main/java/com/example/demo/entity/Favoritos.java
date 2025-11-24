package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;

@Entity
public class Favoritos {

    int id;
    int Usuario_id;
    int Manga_id;
    Date creado_en;

}
