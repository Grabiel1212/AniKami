package com.demo.data.request;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestInfo {

    private int idUsuario;
    private String nombreUsuario;
    private String apellido;
    private File foto;

    public UsuarioRequestInfo(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
