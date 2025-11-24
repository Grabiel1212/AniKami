package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;

@Entity

public class Usuarios {

    int idUsuario

    String NombreUsuario;
    String correo;
    String contrasena;
    String google_id;
    String avatar_url;
    Date creado_en;

}
