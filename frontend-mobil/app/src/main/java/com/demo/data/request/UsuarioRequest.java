package com.demo.data.request;

import java.io.File;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UsuarioRequest {

    private int usuarioId;
    private String nombreUsuario;
    private String apellido;
    private String correo;
    private String contrasena;   // SOLO email
    private String googleId;     // SOLO google
    private File foto;
    private List<Integer> generosFavoritos;

    public UsuarioRequest(String nombreUsuario, String apellido, String correo,
                          String contrasena, String googleId,
                          File foto, List<Integer> generosFavoritos) {

        this.nombreUsuario = nombreUsuario;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.googleId = googleId;
        this.foto = foto;
        this.generosFavoritos = generosFavoritos;
    }

    public UsuarioRequest (String correo , String contrasena){
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public UsuarioRequest (int usuarioId ){
        this.usuarioId = usuarioId;
    }

}
