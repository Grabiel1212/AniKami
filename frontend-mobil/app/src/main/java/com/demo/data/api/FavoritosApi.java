package com.demo.data.api;

import com.demo.data.common.BaseResponse;
import com.demo.data.model.Manga;
import com.demo.data.request.FavoritosRequets;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface FavoritosApi {

    // Listar favoritos
    @POST("favoritos/listar")
    Call<BaseResponse<List<Manga>>> listarMangaFavoritos(
            @Body FavoritosRequets request
    );

    // Agregar favorito
    @POST("favoritos/agregar")
    Call<BaseResponse<Void>> agregarMangaFavoritos(
            @Body FavoritosRequets request
    );

    // Eliminar favorito
    @HTTP(method = "DELETE", path = "favoritos/eliminar", hasBody = true)
    Call<BaseResponse<Void>> eliminarMangaFavoritos(
            @Body FavoritosRequets request
    );



}
