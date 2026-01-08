package com.demo.data.api;

import com.demo.data.common.BaseResponse;
import com.demo.data.model.CodigoResponse;
import com.demo.data.model.LoginData;
import com.demo.data.model.Usuario;
import com.demo.data.request.LoginEmailRequest;
import com.demo.data.request.LoginGoogleRequest;
import com.demo.data.request.UsuarioRequest;
import com.demo.data.request.UsuarioRequestInfo;
import com.demo.data.request.VerificacionRequest;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface UsuarioApi {

    // logear con email y paswoord
    @POST("usuarios/login")
    Call<BaseResponse<Usuario>> loginEmail(@Body LoginEmailRequest request);

    // logearse con google
    @POST("usuarios/login-google")
    Call<BaseResponse<LoginData>> loginGoogle(@Body LoginGoogleRequest request);

    // validacion de email
    @POST("usuarios/email-validacion")
    Call<BaseResponse<Boolean>> verificarEmail(@Body VerificacionRequest request);

    // validacio de email para recuperacion
    @POST("usuarios/verificar-recuperacion")
    Call<BaseResponse<Boolean>> verificarEmailGoogle(@Body VerificacionRequest request);
    @POST("correo/enviar-codigo")
    Call<BaseResponse<CodigoResponse>> enviarCodigoVerificacion(
            @Body VerificacionRequest request
    );

    @POST("usuarios/restablecer-contrasena")
    Call<BaseResponse<Void>> restablecerContrasena(
            @Body UsuarioRequest request
    );


    // registro del usuario con email y password

    @Multipart
    @POST("usuarios/registrar-email")
    Call<BaseResponse<Usuario>> registroUsuarioEmail(
            @Part("nombreUsuario") RequestBody nombreUsuario,
            @Part("apellido") RequestBody apellido,
            @Part("correo") RequestBody correo,
            @Part("contrasena") RequestBody contrasena,
            @Part("generosFavoritos") RequestBody generosFavoritos,
            @Part MultipartBody.Part foto
    );

    // registro del usuario con Google
    @Multipart
    @POST("usuarios/registrar-google")
    Call<BaseResponse<Usuario>> registroUsuarioGoogle(
            @Part("nombreUsuario") RequestBody nombreUsuario,
            @Part("apellido") RequestBody apellido,
            @Part("correo") RequestBody correo,
            @Part("google_id") RequestBody googleId,
            @Part("generosFavoritos") RequestBody generosFavoritos,
            @Part MultipartBody.Part foto
    );


    @POST("usuarios/ver")
    Call<BaseResponse<Usuario>> verInfoUsuario(@Body UsuarioRequestInfo request);

    @Multipart
    @PUT("usuarios/actualizar-perfil")
    Call<BaseResponse<Usuario>> editarUsuario(
            @Part("idUsuario") RequestBody idUsuario,
            @Part("nombreUsuario") RequestBody nombreUsuario,
            @Part("apellido") RequestBody apellido,
            @Part MultipartBody.Part foto // puede ser null
    );







}
