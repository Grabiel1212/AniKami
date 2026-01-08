package com.demo.presentation.activitys.login.action;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.demo.data.common.BaseResponse;
import com.demo.data.model.LoginData;
import com.demo.data.model.Usuario;
import com.demo.data.repocitory.UsuarioRepository;
import com.demo.data.request.VerificacionRequest;
import com.demo.data.session.SessionManager;
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

        usuarioRepository
                .verificarEmail(new VerificacionRequest(email))
                .observeForever(response -> {

                    if (response == null) {
                        Toast.makeText(
                                context,
                                "Error de conexión con el servidor",
                                Toast.LENGTH_LONG
                        ).show();
                        return;
                    }

                    Log.d("LoginGoogle",
                            "Verificación correo: " + new Gson().toJson(response));

                    // ==================================================
                    // 🔵 CORREO NO EXISTE → REGISTRO GOOGLE
                    // success = true
                    // ==================================================
                    if (response.isSuccess()) {

                        Intent intent = new Intent(context, RegisterActivity.class);
                        intent.addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                        );

                        intent.putExtra("tipoRegistro", "GOOGLE");
                        intent.putExtra("googleId", googleId);
                        intent.putExtra("email", email);

                        context.startActivity(intent);
                        return;
                    }

                    // ==================================================
                    // 🟢 CORREO EXISTE → INTENTAR LOGIN GOOGLE
                    // success = false
                    // ==================================================
                    usuarioRepository
                            .loginGoogle(googleId)
                            .observeForever(loginResponse -> {

                                if (loginResponse == null || loginResponse.getData() == null) {
                                    Toast.makeText(
                                            context,
                                            "Error al iniciar sesión",
                                            Toast.LENGTH_LONG
                                    ).show();
                                    return;
                                }

                                Log.d("LoginGoogle",
                                        "Login Google: " + new Gson().toJson(loginResponse));

                                LoginData loginData = loginResponse.getData();

                                // ✅ GOOGLE ID COINCIDE → HOME
                                if (loginResponse.isSuccess()
                                        && loginData.isLogin()
                                        && loginData.getUsuario() != null) {

                                    Usuario usuario = loginData.getUsuario();

// ✅ GUARDAR ID DE USUARIO
                                    SessionManager session = new SessionManager(context);
                                    session.saveUserId(usuario.getIdUsuario());

                                    Toast.makeText(
                                            context,
                                            "Bienvenido " + usuario.getNombreUsuario(),
                                            Toast.LENGTH_LONG
                                    ).show();

                                    Intent intent = new Intent(context, HomeActivity.class);
                                    intent.addFlags(
                                            Intent.FLAG_ACTIVITY_NEW_TASK |
                                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    );
                                    context.startActivity(intent);


                                } else {
                                    // ❌ Correo existe pero es EMAIL/PASSWORD
                                    Toast.makeText(
                                            context,
                                            "Este correo está asociado a una cuenta con correo y contraseña",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            });
                });
    }
}
