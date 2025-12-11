package com.demo.presentation.activitys.login.action;

import android.content.Context;
import android.widget.Toast;

public class LoginGoogle {

    private Context context;
    private String googleId;
    private String email;
    private String nombre;
    private String foto;
    private String idToken;

    public LoginGoogle(Context context, String googleId, String email, String nombre, String foto, String idToken) {
        this.context = context;
        this.googleId = googleId;
        this.email = email;
        this.nombre = nombre;
        this.foto = foto;
        this.idToken = idToken;
    }

    public void iniciarSesion() {

        // Aquí implementas tu llamada a la API usando Retrofit, Volley o HttpURLConnection
        // Ejemplo ficticio:

        /*
        ApiClient.getService().loginGoogle(new LoginGoogleRequest(googleId, email, nombre, foto, idToken))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            // Guardar token, navegar a Home, etc.
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
        */

        // Por ahora solo mostramos:
        Toast.makeText(context,
                "Google OK\nID: " + googleId,
                Toast.LENGTH_LONG).show();
    }
}
