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

@Entity
@Table(name = "mangas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mangas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "titulo")
    String titulo;
    @Column(name = "descripcion")
    String descripcion;
    @Column(name = "estado")
    String estado;
    @Column(name = "portada_url")
    String portada_url;
    @Column(name = "creado_en")
    private Date creadoEn;

}
