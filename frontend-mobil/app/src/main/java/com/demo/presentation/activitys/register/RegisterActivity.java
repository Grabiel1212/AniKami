package com.demo.presentation.activitys.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.demo.R;
import com.demo.presentation.activitys.home.HomeActivity;
import com.demo.presentation.activitys.register.action.RegisterViewModel;
import com.demo.presentation.activitys.register.cammon.PreferenciasLoander;
import com.demo.presentation.activitys.register.components.RegisterFlowManager;
import com.demo.presentation.activitys.register.components.TipoRegistro;
import com.demo.presentation.activitys.register.manager.PermissionManager;
import com.demo.presentation.util.FileUtils;
import com.demo.presentation.util.Validator;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private boolean enviando = false;

    private static final String TAG = "RegisterActivity";

    // --- UI ---
    private ViewFlipper flipper;
    private Button btnSiguiente, btnAtras;
    private ImageView imgPreview;
    private TextView txtSeleccionarFoto, txtOmitirFoto, txtOmitirPreferencias;
    private ChipGroup chipGroup;

    // --- EditTexts ---
    private EditText edtCorreo;
    private EditText edtNombre;
    private EditText edtApellido;
    private EditText edtContrasena;
    private EditText edtConfirmarContrasena;

    // --- Managers / VM ---
    private RegisterFlowManager flow;
    private RegisterViewModel viewModel;
    private PermissionManager permissionManager;

    // --- Estado ---
    private String googleId;
    private String email;
    private TipoRegistro tipoRegistro;
    private File fotoSeleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        obtenerDatosIntent();
        initViewModel();
        initViews();
        initManagers();
        initClicks();
        precargarDatos();
        actualizarVista();

    }

    // -------------------------------------------------------------------------
    // INIT
    // -------------------------------------------------------------------------

    private void obtenerDatosIntent() {
        googleId = getIntent().getStringExtra("googleId");
        email = getIntent().getStringExtra("email");

        String tipo = getIntent().getStringExtra("tipoRegistro");
        tipoRegistro = "GOOGLE".equals(tipo)
                ? TipoRegistro.GOOGLE
                : TipoRegistro.EMAIL;

        flow = new RegisterFlowManager(tipoRegistro);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    private void initViews() {
        flipper = findViewById(R.id.flipper);
        btnSiguiente = findViewById(R.id.btn_siguiente_resgistrar);
        btnAtras = findViewById(R.id.btn_atras_registrar);
        imgPreview = findViewById(R.id.img_preview_foto);

        txtSeleccionarFoto = findViewById(R.id.txt_seleccionar_foto);
        txtOmitirFoto = findViewById(R.id.txt_omitir_foto);
        txtOmitirPreferencias = findViewById(R.id.txt_omitir_preferencias);

        chipGroup = findViewById(R.id.chipGroupPreferencias);

        edtCorreo = findViewById(R.id.edt_email_register);
        edtNombre = findViewById(R.id.edt_nombre_registro);
        edtApellido = findViewById(R.id.edt_apellido_registro);
        edtContrasena = findViewById(R.id.edt_contraseña_registro);
        edtConfirmarContrasena = findViewById(R.id.edt_confirmarcontraseña_registro);

        cargarGeneros();
    }

    private void initManagers() {
        permissionManager = new PermissionManager(
                this,
                imgPreview,
                uri -> {
                    fotoSeleccionada = FileUtils.uriToFile(this, uri);

                    if (fotoSeleccionada != null) {
                        Log.d(TAG, "Archivo listo: " + fotoSeleccionada.getAbsolutePath());
                        Toast.makeText(this, "✓ Foto preparada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "✗ Error al procesar la foto", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


    private void initClicks() {

        btnSiguiente.setOnClickListener(v -> manejarSiguiente());

        btnAtras.setOnClickListener(v -> {
            flow.anteriorPaso();
            actualizarVista();
        });

        txtSeleccionarFoto.setOnClickListener(v ->
                permissionManager.verificarPermisos()
        );

        txtOmitirFoto.setOnClickListener(v -> {
            fotoSeleccionada = null;
            flow.siguientePaso();
            actualizarVista();
        });


        txtOmitirPreferencias.setOnClickListener(v ->
                viewModel.setGenerosSeleccionados(new ArrayList<>())
        );
    }

    // -------------------------------------------------------------------------
    // DATA
    // -------------------------------------------------------------------------

    private void cargarGeneros() {
        viewModel.listarGeneros().observe(this, response -> {
            if (response.isSuccess() && response.getData() != null) {
                PreferenciasLoander.cargarGeneros(this, chipGroup, response.getData());
            }
        });
    }

    private List<Integer> obtenerGenerosSeleccionados() {
        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            if (chip.isChecked()) {
                ids.add((Integer) chip.getTag());
            }
        }
        return ids;
    }

    // -------------------------------------------------------------------------
    // FLOW
    // -------------------------------------------------------------------------

    private void manejarSiguiente() {

        if (enviando) return; // 🚫 evita doble envío

        int paso = flow.getPasoActual().ordinal();

        // PASO 0 → verificar correo (solo EMAIL)
        if (paso == 0 && tipoRegistro == TipoRegistro.EMAIL) {
            validarYVerificarCorreo();
            return;
        }

        // PASO 1 → datos personales
        if ((paso == 1 && tipoRegistro == TipoRegistro.EMAIL) ||
                (paso == 0 && tipoRegistro == TipoRegistro.GOOGLE)) {
            validarDatosPersonales();
            return;
        }

        // PASO 2 → contraseña (solo EMAIL)
        if (paso == 2 && tipoRegistro == TipoRegistro.EMAIL) {
            validarContrasena();
            return;
        }

        // ÚLTIMO PASO → enviar registro
        if (flow.esUltimoPaso()) {
            enviarRegistroFinal();
            return;
        }

        // Paso normal
        flow.siguientePaso();
        actualizarVista();
    }


    private void validarYVerificarCorreo() {
        if (!Validator.with(edtCorreo).required("Obligatorio").email().validate()) return;

        String correo = edtCorreo.getText().toString().trim();

        viewModel.verificarCorreo(correo).observe(this, res -> {
            if (res.isSuccess()) {
                viewModel.setCorreo(correo);
                flow.siguientePaso();
                actualizarVista();
            } else {
                edtCorreo.setError(res.getMessage());
            }
        });
    }

    private void validarDatosPersonales() {
        boolean nombreValido = Validator.with(edtNombre).required("Obligatorio").validate();
        boolean apellidoValido = Validator.with(edtApellido).required("Obligatorio").validate();

        if (nombreValido && apellidoValido) {
            flow.siguientePaso();
            actualizarVista();
        }
    }

    private void validarContrasena() {
        boolean valida = Validator.with(edtContrasena)
                .required("Obligatorio")
                .min(4, "Mínimo 4")
                .validate();

        if (!valida) return;

        if (!edtContrasena.getText().toString()
                .equals(edtConfirmarContrasena.getText().toString())) {
            edtConfirmarContrasena.setError("No coinciden");
            return;
        }

        flow.siguientePaso();
        actualizarVista();
    }

    // -------------------------------------------------------------------------
    // UI
    // -------------------------------------------------------------------------

    private void precargarDatos() {
        if (tipoRegistro == TipoRegistro.GOOGLE && email != null) {
            edtCorreo.setText(email);
            edtCorreo.setEnabled(false);
            viewModel.setCorreo(email);
        }
    }

    private void mostrarCargando() {
        enviando = true;
        btnSiguiente.setEnabled(false);
        btnSiguiente.setText("Registrando...");
        btnAtras.setEnabled(false);
    }

    private void ocultarCargando() {
        enviando = false;
        btnSiguiente.setEnabled(true);
        btnSiguiente.setText("Finalizar");
        btnAtras.setEnabled(true);
    }


    private void actualizarVista() {
        flipper.setDisplayedChild(flow.getPasoActual().ordinal());

        btnAtras.setEnabled(!flow.esPrimerPaso());
        btnAtras.setAlpha(flow.esPrimerPaso() ? 0.5f : 1f);

        btnSiguiente.setText(flow.esUltimoPaso() ? "Finalizar" : "Siguiente");
    }

    // -------------------------------------------------------------------------
    // PERMISSIONS
    // -------------------------------------------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.onRequestPermissionsResult(requestCode, grantResults);
    }


    private void enviarRegistroFinal() {

        mostrarCargando(); // 🔄 UI loading

        // 🔹 Géneros seleccionados
        List<Integer> generos = obtenerGenerosSeleccionados();
        viewModel.setGenerosSeleccionados(generos);

        String nombre = edtNombre.getText().toString().trim();
        String apellido = edtApellido.getText().toString().trim();
        String correoFinal = viewModel.getCorreo();

        if (tipoRegistro == TipoRegistro.EMAIL) {

            String contrasena = edtContrasena.getText().toString().trim();

            viewModel.registrarUsuarioEmail(
                    nombre,
                    apellido,
                    correoFinal,
                    contrasena,
                    fotoSeleccionada
            ).observe(this, response -> {

                ocultarCargando();

                if (response.isSuccess()) {
                    Toast.makeText(this, "Registro exitoso 🎉", Toast.LENGTH_LONG).show();
                    irAHome(); // ✅ HOME
                } else {
                    Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } else { // GOOGLE

            viewModel.registrarUsuarioGoogle(
                    nombre,
                    apellido,
                    correoFinal,
                    googleId,
                    fotoSeleccionada
            ).observe(this, response -> {

                ocultarCargando();

                if (response.isSuccess()) {
                    Toast.makeText(this, "Registro Google exitoso 🎉", Toast.LENGTH_LONG).show();
                    irAHome(); // ✅ HOME
                } else {
                    Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    private void irAHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
