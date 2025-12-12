package com.demo.presentation.activitys.login.action;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.demo.data.common.BaseResponse;
import com.demo.data.model.Usuario;

import com.demo.data.repocitory.UsuarioRepository;
import com.demo.presentation.activitys.home.HomeActivity;

public class LoginEmail {

    private final String usuario;
    private final String pass;
    private final Activity activity;
    private final UsuarioRepository usuarioRepository;

    public LoginEmail(Activity activity, String usuario, String pass) {
        this.activity = activity;
        this.usuario = usuario;
        this.pass = pass;
        this.usuarioRepository = new UsuarioRepository();
    }

    public String getUsuario() { return usuario; }
    public String getPass() { return pass; }

    public void iniciarSesion() {
        usuarioRepository.loginEmail(usuario, pass)
                .observe((AppCompatActivity) activity, new Observer<BaseResponse<Usuario>>() {
                    @Override
                    public void onChanged(BaseResponse<Usuario> response) {
                        if (response.isSuccess() && response.getData() != null) {
                            Intent intent = new Intent(activity, HomeActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        } else {
                            Toast.makeText(activity, "Error: " + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
