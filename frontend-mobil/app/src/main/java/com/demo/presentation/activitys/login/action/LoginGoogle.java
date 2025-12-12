package com.demo.presentation.activitys.login.action;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.demo.data.common.BaseResponse;
import com.demo.data.model.LoginData;
import com.demo.data.model.Usuario;
import com.demo.data.repocitory.UsuarioRepository;
import com.demo.presentation.activitys.home.HomeActivity;
import com.demo.presentation.activitys.register.RegisterActivity;
import com.google.gson.Gson;

public class LoginGoogle {

    private final Context context;
    private final String googleId;
    private final String email;
    private final UsuarioRepository usuarioRepository;

    public LoginGoogle(Context context, String googleId, String email) {
        this.context = context;
        this.googleId = googleId;
        this.email = email;
        this.usuarioRepository = new UsuarioRepository();
    }

    public void iniciarSesion() {
        usuarioRepository.loginGoogle(googleId)
                .observeForever(new Observer<BaseResponse<LoginData>>() {
                    @Override
                    public void onChanged(BaseResponse<LoginData> response) {

                        if (response == null) {
                            Toast.makeText(context, "Error en la conexión con el servidor", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Log.d("LoginGoogle", "Respuesta servidor: " + new Gson().toJson(response));

                        LoginData loginData = response.getData();

                        if (response.isSuccess() && loginData != null && loginData.isLogin() && loginData.getUsuario() != null) {

                            Usuario usuario = loginData.getUsuario();
                            Toast.makeText(context, "Bienvenido " + usuario.getNombreUsuario(), Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(context, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);

                        } else {

                            Toast.makeText(context, "Completa tu registro", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(context, RegisterActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            // INFORMAMOS EXPLÍCITAMENTE QUE ES REGISTRO GOOGLE
                            intent.putExtra("tipoRegistro", "GOOGLE");
                            intent.putExtra("googleId", googleId);
                            intent.putExtra("email", email);

                            context.startActivity(intent);
                        }
                    }
                });
    }
}
