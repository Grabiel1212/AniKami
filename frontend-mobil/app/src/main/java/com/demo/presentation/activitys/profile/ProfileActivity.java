package com.demo.presentation.activitys.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.demo.R;
import com.demo.data.model.Genero;
import com.demo.data.session.SessionManager;
import com.demo.presentation.activitys.base.BaseActivity;
import com.demo.presentation.activitys.profile.action.ProfileViewModel;
import com.demo.presentation.activitys.register.manager.PermissionManager;
import com.demo.presentation.util.FileUtils;
import com.demo.presentation.util.Validator;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileActivity extends BaseActivity
        implements PermissionManager.OnImageSelectedListener {

    private EditText etNombre, etApellido, etCorreo;
    private ImageView imgProfile;
    private Button btnEdit;
    private ChipGroup chipGroupGeneros;

    private PermissionManager permissionManager;
    private ProfileViewModel viewModel;
    private SessionManager sessionManager;

    private boolean editando = false;
    private Uri fotoSeleccionada;
    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        setupBottomNav(R.id.nav_profile);

        sessionManager = new SessionManager(this);
        idUsuario = sessionManager.getUserId();

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        initViews();
        configurarEditarPerfil(); // solo listeners y lógica UI
        cargarGeneros();
        cargarInfoUsuario();
    }


    @Override
    protected void onResume() {
        super.onResume();
        refrescarDatos();
    }

    private void refrescarDatos() {


    }

    private void initViews() {
        etNombre = findViewById(R.id.et_nombre);
        etApellido = findViewById(R.id.et_apellido);
        etCorreo = findViewById(R.id.et_correo);
        imgProfile = findViewById(R.id.img_profile);
        btnEdit = findViewById(R.id.btn_edit_profile);
        chipGroupGeneros = findViewById(R.id.chipGroupGeneros);

        // === COLORES ===
        etNombre.setTextColor(getResources().getColor(android.R.color.white));
        etApellido.setTextColor(getResources().getColor(android.R.color.white));

        // Correo en gris y bloqueado
        etCorreo.setTextColor(getResources().getColor(R.color.gray_medium));
        etCorreo.setEnabled(false);
        etCorreo.setFocusable(false);
        etCorreo.setCursorVisible(false);

        permissionManager = new PermissionManager(this, imgProfile, this);
    }


    /* ================= INFO USUARIO ================= */

    private void cargarInfoUsuario() {
        viewModel.verInfoUsuario(idUsuario).observe(this, response -> {
            if (response != null && response.isSuccess()) {

                etNombre.setText(response.getData().getNombreUsuario());
                etApellido.setText(response.getData().getApellido());
                etCorreo.setText(response.getData().getCorreo());

                if (response.getData().getFoto() != null &&
                        !response.getData().getFoto().isEmpty()) {

                    Glide.with(this)
                            .load(response.getData().getFoto())
                            .into(imgProfile);
                }

            } else {
                Toast.makeText(this, "Error al cargar perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* ================= PREFERENCIAS ================= */

    private void cargarGeneros() {
        viewModel.obtenerGeneros(idUsuario).observe(this, response -> {
            if (response != null && response.isSuccess()) {

                chipGroupGeneros.removeAllViews();

                for (Genero genero : response.getData()) {
                    Chip chip = new Chip(this);
                    chip.setText(genero.getNombre()); // SOLO NOMBRE
                    chip.setCheckable(false);
                    chipGroupGeneros.addView(chip);
                }
            }
        });
    }

    /* ================= EDITAR PERFIL ================= */

    private void configurarEditarPerfil() {

        btnEdit.setOnClickListener(v -> {
            editando = !editando;

            if (editando) {
                habilitarEdicion(true);
                btnEdit.setText("Guardar");
            } else {
                habilitarEdicion(false);
                btnEdit.setText("Editar perfil");
                guardarCambios();
            }
        });

        imgProfile.setOnClickListener(v -> {
            if (editando) {
                permissionManager.verificarPermisos();
            }
        });
    }

    private void habilitarEdicion(boolean estado) {
        etNombre.setEnabled(estado);
        etApellido.setEnabled(estado);

        etNombre.setFocusableInTouchMode(estado);
        etApellido.setFocusableInTouchMode(estado);

        // El correo JAMÁS se habilita
        etCorreo.setEnabled(false);
        etCorreo.setFocusable(false);
    }


    private void guardarCambios() {

        // ===== VALIDACIONES =====
        boolean valido = true;

        valido &= Validator.with(etNombre)
                .required("El nombre es obligatorio")
                .min(3, "El nombre debe tener al menos 3 caracteres")
                .validate();

        valido &= Validator.with(etApellido)
                .required("El apellido es obligatorio")
                .min(3, "El apellido debe tener al menos 3 caracteres")
                .validate();

        // ❌ NO validar correo
        if (!valido) return;
        // ========================

        RequestBody rbId = RequestBody.create(
                String.valueOf(idUsuario),
                MultipartBody.FORM
        );

        RequestBody rbNombre = RequestBody.create(
                etNombre.getText().toString().trim(),
                MultipartBody.FORM
        );

        RequestBody rbApellido = RequestBody.create(
                etApellido.getText().toString().trim(),
                MultipartBody.FORM
        );

        MultipartBody.Part fotoPart = null;

        if (fotoSeleccionada != null) {
            File file = FileUtils.uriToFile(this, fotoSeleccionada);

            if (file != null) {
                RequestBody rbFoto =
                        RequestBody.create(file, MediaType.parse("image/*"));

                fotoPart = MultipartBody.Part.createFormData(
                        "foto",
                        file.getName(),
                        rbFoto
                );
            }
        }

        viewModel.editarUsuario(rbId, rbNombre, rbApellido, fotoPart)
                .observe(this, response -> {
                    if (response != null && response.isSuccess()) {
                        Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                        cargarInfoUsuario();
                    } else {
                        Toast.makeText(this, "Error al actualizar perfil", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /* ================= GALERÍA ================= */

    @Override
    public void onImageSelected(Uri imageUri) {
        fotoSeleccionada = imageUri;
        imgProfile.setImageURI(imageUri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.onRequestPermissionsResult(requestCode, grantResults);
    }
}
