package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginaDTO {
    private Integer numeroPagina;
    private String imagenUrl;
}