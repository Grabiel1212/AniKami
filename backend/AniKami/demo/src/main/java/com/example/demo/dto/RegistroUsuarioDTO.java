package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor

public class RegistroUsuarioDTO {
    private String nombreUsuario;
    private String correo;
    private String contrasena;
    private String googleId;

    private List<Integer> generosFavoritos;

}
