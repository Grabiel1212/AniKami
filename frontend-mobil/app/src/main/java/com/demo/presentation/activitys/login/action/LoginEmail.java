package com.demo.presentation.activitys.login.action;

import android.app.Activity;
import android.content.Intent;

import com.demo.presentation.activitys.home.HomeActivity;

public class LoginEmail {

    private final String usuario;
    private final String pass;
    private final Activity activity;

    public LoginEmail(Activity activity, String usuario, String pass) {
        this.activity = activity;
        this.usuario = usuario;
        this.pass = pass;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPass() {
        return pass;
    }

    public void iniciarSesion() {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
