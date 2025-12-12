package com.demo.data.model;


import java.util.Date;

import lombok.Data;

@Data
public class Usuario {

    private Integer idUsuario;
    private String nombreUsuario;
    private String correo;
    private String contrasena;
    private String googleId;
    private String foto;
    private Date creadoEn;
}
