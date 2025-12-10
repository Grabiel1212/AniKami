package com.example.demo.Model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idUsuario;
    @Column(name = "NombreUsuario")
    String NombreUsuario;
    @Column(name = "correo")
    String correo;
    @Column(name = "Contrasena")
    String contrasena;
    @Column(name = "Google_ID")
    String google_id;
    @Column(name = "foto")
    String foto;
    @Column(name = "Creado_En")
    Date creado_en;

}
