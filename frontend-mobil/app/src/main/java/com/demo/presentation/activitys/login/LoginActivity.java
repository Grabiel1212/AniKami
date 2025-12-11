package com.demo.presentation.activitys.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.demo.R;
import com.demo.presentation.activitys.login.action.LoginEmail;
import com.demo.presentation.activitys.login.action.LoginGoogle;
import com.demo.presentation.activitys.recovery.RecoveryActivity;
import com.demo.presentation.activitys.register.RegisterActivity;
import com.demo.presentation.util.Validator;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;

    private Button btnLogin;

    private Button btnLoginGoogle;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_GOOGLE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initWiev();
    }

    private void initWiev() {
        edtEmail = findViewById(R.id.edt_usuario_login);
        edtPassword = findViewById(R.id.edt_pass_login);
        btnLogin = findViewById(R.id.btn_inicarsesion);
        btnLoginGoogle = findViewById(R.id.btn_google_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestIdToken("272624333102-4cash1dutdoils05dc3eq2uf3a6q7ffs.apps.googleusercontent.com") // <-- WEB CLIENT ID
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    public void resgisterOnclick(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void recoveryOnclick(View v){
        Intent intent = new Intent(this, RecoveryActivity.class);
        startActivity(intent);
    }

    public void onClickLoginEmail(View v){

        boolean isValidEmail = Validator.with(edtEmail)
                .required("El correo es obligatorio")
                .email()
                .validate();

        if (!isValidEmail) return;

        boolean isValidPass = Validator.with(edtPassword)
                .required("La contraseña es obligatoria")
                .min(4, "Debe tener mínimo 4 caracteres")
                .validate();

        if (!isValidPass) return;

        LoginEmail login = new LoginEmail(
                this,
                edtEmail.getText().toString(),
                edtPassword.getText().toString()
        );
        login.iniciarSesion();


    }

    public void onClickLoginGoogle(View v){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleResult(task);
        }
    }

    private void handleGoogleResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String googleId = account.getId();  // sub
            String email = account.getEmail();
            String nombre = account.getDisplayName();
            String foto = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : null;

            String idToken = account.getIdToken(); // Token para tu backend

            // Llamar a tu acción LoginGoogle
            LoginGoogle loginGoogle = new LoginGoogle(
                    this,
                    googleId,
                    email,
                    nombre,
                    foto,
                    idToken
            );

            loginGoogle.iniciarSesion();

        } catch (ApiException e) {
            e.printStackTrace();
        }
    }





}