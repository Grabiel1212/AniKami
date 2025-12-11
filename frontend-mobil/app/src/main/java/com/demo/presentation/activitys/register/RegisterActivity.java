package com.demo.presentation.activitys.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.R;
import com.demo.presentation.activitys.register.cammon.PreferenciasLoander;
import com.demo.presentation.activitys.register.components.RegisterFlowManager;
import com.demo.presentation.activitys.register.components.TipoRegistro;
import com.demo.presentation.activitys.register.manager.PermissionManager;
import com.google.android.material.chip.ChipGroup;

public class RegisterActivity extends AppCompatActivity {

    private ViewFlipper flipper;
    private Button btnSiguiente, btnAtras;
    private RegisterFlowManager flow;
    private ImageView imgPreview;
    private TextView txtSeleccionarFoto, txtOmitirFoto, txtOmitirPreferencias;
    private PermissionManager permissionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initManagers();
        initClicks();
        actualizarVista();
    }


    private void initViews() {
        flipper = findViewById(R.id.flipper);
        btnSiguiente = findViewById(R.id.btn_siguiente_resgistrar);
        btnAtras = findViewById(R.id.btn_atras_registrar);

        imgPreview = findViewById(R.id.img_preview_foto);
        txtSeleccionarFoto = findViewById(R.id.txt_seleccionar_foto);
        txtOmitirFoto = findViewById(R.id.txt_omitir_foto);
        txtOmitirPreferencias = findViewById(R.id.txt_omitir_preferencias);

        ChipGroup chipGroup = findViewById(R.id.chipGroupPreferencias);
        PreferenciasLoander.cargarPreferencias(this, chipGroup);
    }

    private void initManagers() {
        flow = new RegisterFlowManager(TipoRegistro.EMAIL);
        permissionManager = new PermissionManager(this, imgPreview);
    }

    private void initClicks() {

        btnSiguiente.setOnClickListener(v -> {
            flow.siguientePaso();
            actualizarVista();
        });

        btnAtras.setOnClickListener(v -> {
            flow.anteriorPaso();
            actualizarVista();
        });

        txtSeleccionarFoto.setOnClickListener(v -> permissionManager.verificarPermisos());

        txtOmitirFoto.setOnClickListener(v -> {
            flow.siguientePaso();
            actualizarVista();
        });

        txtOmitirPreferencias.setOnClickListener(v -> {
            flow.siguientePaso();
            actualizarVista();
        });
    }


    // =============================
    //        UI
    // =============================

    private void actualizarVista() {

        flipper.setDisplayedChild(flow.getPasoActual().ordinal());

        btnAtras.setEnabled(!flow.esPrimerPaso());
        btnAtras.setAlpha(flow.esPrimerPaso() ? 0.5f : 1f);

        btnSiguiente.setText(flow.esUltimoPaso() ? "Finalizar" : "Siguiente");
    }


    // =============================
    //   PERMISOS/GALERÍA
    // =============================

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        permissionManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
