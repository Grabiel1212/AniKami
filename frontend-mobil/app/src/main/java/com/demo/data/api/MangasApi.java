package com.demo.data.api;

import com.demo.data.common.BaseResponse;

import com.demo.data.model.Capitulo;
import com.demo.data.model.Manga;
import com.demo.data.model.MangaDetalle;
import com.demo.data.model.Pajina;
import com.demo.data.request.GeneroRequest;
import com.demo.data.request.PaginaRequets;
import com.demo.data.request.MangaRequest;
import com.demo.data.request.UsuarioRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MangasApi {

    @GET("mangas/listar")
    Call<BaseResponse<List<Manga>>> listarMangas();

    @GET("mangas/top10")
    Call<BaseResponse<List<Manga>>> listarTop10();

    @GET("mangas/populares")
    Call<BaseResponse<List<Manga>>> listarMangasPopulares();

    @POST("mangas/recomendados")
    Call<BaseResponse<List<Manga>>> listarMangaPreferencias(
            @Body UsuarioRequest request
    );

    //para ver el detalle del manga
    @POST("mangas/detalle")
    Call<BaseResponse<MangaDetalle>> verMangaDetalle(
            @Body MangaRequest request
    );

    // para listar los capitulos del manga segun su rango
    @POST("mangas/capitulos/por-rango")
    Call<BaseResponse<List<Capitulo>>> listarCapitulosRango(
            @Body MangaRequest request
    );

    // para ver las pajinas de los capitulos
    @POST("mangas/paginas/por-capitulo")
    Call<BaseResponse<List<Pajina>>> listarPajinaCapitulo(
            @Body PaginaRequets request
    );

    @POST("mangas/por-genero")
    Call<BaseResponse<List<Manga>>> listarMangaGenero(
            @Body GeneroRequest request
    );



}

