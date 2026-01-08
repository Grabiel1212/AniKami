package com.demo.data.repocitory;

import androidx.lifecycle.LiveData;

import com.demo.data.api.RetrofitClient;
import com.demo.data.api.UsuarioApi;
import com.demo.data.common.BaseResponse;
import com.demo.data.helpers.LiveDataCallAdapter;
import com.demo.data.model.CodigoResponse;
import com.demo.data.model.LoginData;
import com.demo.data.model.Usuario;
import com.demo.data.request.LoginEmailRequest;
import com.demo.data.request.LoginGoogleRequest;
import com.demo.data.request.UsuarioRequest;
import com.demo.data.request.UsuarioRequestInfo;
import com.demo.data.request.VerificacionRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UsuarioRepository {

    private final UsuarioApi usuarioApi;
    private final String TAG = UsuarioRepository.class.getSimpleName();

    public UsuarioRepository() {
        usuarioApi = RetrofitClient.getRetrofit().create(UsuarioApi.class);
    }

    public UsuarioApi getUsuarioApi() {
        return usuarioApi;
    }

    public LiveData<BaseResponse<Usuario>> loginEmail(String correo, String contrasena) {
        LoginEmailRequest request = new LoginEmailRequest(correo, contrasena);
        return LiveDataCallAdapter.call(usuarioApi.loginEmail(request));
    }

    public LiveData<BaseResponse<LoginData>> loginGoogle(String googleId) {
        LoginGoogleRequest request = new LoginGoogleRequest(googleId);
        return LiveDataCallAdapter.call(
                usuarioApi.loginGoogle(request)
        );
    }

    // 🔹 Verificar correo normal
    public LiveData<BaseResponse<Boolean>> verificarEmail(VerificacionRequest request) {
        return LiveDataCallAdapter.call(
                usuarioApi.verificarEmail(request)
        );
    }

    // 🔹 Verificar correo Google (recuperación)
    public LiveData<BaseResponse<Boolean>> verificarEmailGoogle(VerificacionRequest request) {
        return LiveDataCallAdapter.call(
                usuarioApi.verificarEmailGoogle(request)
        );
    }

    // 🔹 Enviar código de verificación al correo
    public LiveData<BaseResponse<CodigoResponse>> enviarCodigoVerificacion(VerificacionRequest request) {
        return LiveDataCallAdapter.call(
                usuarioApi.enviarCodigoVerificacion(request)
        );
    }


    // 🔹 Restablecer contraseña
    public LiveData<BaseResponse<Void>> restablecerContrasena(String correo, String contrasena) {
        UsuarioRequest request = new UsuarioRequest(correo, contrasena);

        return LiveDataCallAdapter.call(usuarioApi.restablecerContrasena(request));
    }


    public LiveData<BaseResponse<Usuario>> registrarUsuarioEmail(
            RequestBody nombreUsuario,
            RequestBody apellido,
            RequestBody correo,
            RequestBody contrasena,
            RequestBody generosFavoritos,
            MultipartBody.Part foto
    ) {
        return LiveDataCallAdapter.call(
                usuarioApi.registroUsuarioEmail(
                        nombreUsuario,
                        apellido,
                        correo,
                        contrasena,
                        generosFavoritos,
                        foto
                )
        );
    }

    public LiveData<BaseResponse<Usuario>> registrarUsuarioGoogle(
            RequestBody nombreUsuario,
            RequestBody apellido,
            RequestBody correo,
            RequestBody googleId,
            RequestBody generosFavoritos,
            MultipartBody.Part foto
    ) {
        return LiveDataCallAdapter.call(
                usuarioApi.registroUsuarioGoogle(
                        nombreUsuario,
                        apellido,
                        correo,
                        googleId,
                        generosFavoritos,
                        foto
                )
        );
    }

    /* ========================= VER INFO USUARIO ========================= */

    public LiveData<BaseResponse<Usuario>> verInfoUsuario(int idUsuario) {
        UsuarioRequestInfo request = new UsuarioRequestInfo(idUsuario);
        return LiveDataCallAdapter.call(usuarioApi.verInfoUsuario(request));
    }

    /* ========================= EDITAR PERFIL ========================= */

    public LiveData<BaseResponse<Usuario>> editarUsuario(
            RequestBody idUsuario,
            RequestBody nombreUsuario,
            RequestBody apellido,
            MultipartBody.Part foto // puede ser null
    ) {
        return LiveDataCallAdapter.call(
                usuarioApi.editarUsuario(
                        idUsuario,
                        nombreUsuario,
                        apellido,
                        foto
                )
        );
    }
}





