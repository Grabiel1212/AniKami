package com.demo.data.api;

import com.demo.data.common.BaseResponse;

import com.demo.data.model.Genero;
import com.demo.data.request.LoginEmailRequest;
import com.demo.data.request.UsuarioRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface GeneroApi {
    @GET("generos/listar")
    Call<BaseResponse<List<Genero>>> listarGeneros();

    @POST("generos/preferencias")
    Call<BaseResponse<List<Genero>>> PreferenciaGeneros(@Body UsuarioRequest request);

}
