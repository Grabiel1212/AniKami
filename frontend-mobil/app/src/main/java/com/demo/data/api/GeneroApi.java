package com.demo.data.api;

import com.demo.data.common.BaseResponse;

import com.demo.data.model.Genero;
import com.demo.data.request.LoginEmailRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;


public interface GeneroApi {
    @GET("generos/listar")
    Call<BaseResponse<List<Genero>>> listarGeneros();

}
