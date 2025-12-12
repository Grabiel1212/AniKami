package com.demo.data.api;

import com.demo.data.common.BaseResponse;
import com.demo.data.model.LoginData;
import com.demo.data.model.Usuario;
import com.demo.data.request.LoginEmailRequest;
import com.demo.data.request.LoginGoogleRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioApi {
    @POST("usuarios/login")
    Call<BaseResponse<Usuario>> loginEmail(@Body LoginEmailRequest request);

    @POST("usuarios/registrar-google")
    Call<BaseResponse<LoginData>> loginGoogle(@Body LoginGoogleRequest request);


}
