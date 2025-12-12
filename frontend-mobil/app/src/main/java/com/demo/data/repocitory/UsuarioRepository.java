package com.demo.data.repocitory;

import androidx.lifecycle.LiveData;

import com.demo.data.api.RetrofitClient;
import com.demo.data.api.UsuarioApi;
import com.demo.data.common.BaseResponse;
import com.demo.data.helpers.LiveDataCallAdapter;
import com.demo.data.model.LoginData;
import com.demo.data.model.Usuario;
import com.demo.data.request.LoginEmailRequest;
import com.demo.data.request.LoginGoogleRequest;

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

    public LiveData<BaseResponse<LoginData>> loginGoogle(String idGoogle) {
        LoginGoogleRequest request = new LoginGoogleRequest(idGoogle);
        return LiveDataCallAdapter.call(usuarioApi.loginGoogle(request));
    }







}
