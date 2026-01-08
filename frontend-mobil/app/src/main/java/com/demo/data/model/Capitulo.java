package com.demo.data.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Capitulo {
         private int id;
         private String imagen_url;
         private int manga_id;
         private int numero;
         private Date publicado_en ;
         private String titulo;
}
