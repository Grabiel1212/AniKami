package com.demo.presentation.activitys.home.adapter;

public class MangaUIModel {

    private int idManga;
    private String titulo;
    private String estado;
    private String portadaUrl;

    public MangaUIModel(String titulo, String estado, String portadaUrl) {
        this.titulo = titulo;
        this.estado = estado;
        this.portadaUrl = portadaUrl;
    }

    public MangaUIModel(int idManga, String titulo, String estado, String portadaUrl) {
        this.idManga = idManga;
        this.titulo = titulo;
        this.estado = estado;
        this.portadaUrl = portadaUrl;
    }


    public int getIdManga() {
        return idManga;
    }
    public String getTitulo() {
        return titulo;
    }

    public String getEstado() {
        return estado;
    }

    public String getPortadaUrl() {
        return portadaUrl;
    }
}