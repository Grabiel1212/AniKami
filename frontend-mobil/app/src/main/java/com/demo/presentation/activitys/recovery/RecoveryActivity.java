package com.demo.presentation.activitys.recovery;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.demo.R;
import com.demo.presentation.activitys.recovery.action.RecoveryViewModel;
import com.demo.presentation.activitys.recovery.components.RecoveryFlowManager;
import com.demo.presentation.activitys.recovery.components.RegistroPasosRecovery;
import com.demo.presentation.util.Validator;

public class RecoveryActivity extends AppCompatActivity {

    private Button btnSiguiente, btnAtras;
    private ViewFlipper flipper;

    private EditText edtCorreo, edtCodigo, edtContrasena, edtConfirmarContrasena;
    private TextView txtReenviarCodigo;

    private RecoveryViewModel viewModel;
    private RecoveryFlowManager flowManager;

    private int codigoBackend = 0;
    private boolean codigoValidado = false;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recovery);

        initViews();
        initViewModel();
        initEventos();
    }

    // ===================== INIT =====================

    private void initViews() {
        btnSiguiente = findViewById(R.id.btn_siguiente_recovery);
        btnAtras = findViewById(R.id.btn_atras_recovery);
        flipper = findViewById(R.id.flipper_recovery);

        edtCorreo = findViewById(R.id.edt_email_recovery);
        edtCodigo = findViewById(R.id.edt_codigoverificacion_recovery);
        edtContrasena = findViewById(R.id.edt_contraseña_recovery);
        edtConfirmarContrasena = findViewById(R.id.edt_confirmarcontraseña_recovery);
        txtReenviarCodigo = findViewById(R.id.lbl_reenviarcodigo_recovery);

        flowManager = new RecoveryFlowManager();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(RecoveryViewModel.class);
    }

    private void initEventos() {

        btnSiguiente.setOnClickListener(v -> {
            if (isLoading) return;

            switch (flowManager.getPasoActual()) {
                case EMAIL:
                    verificarCorreo();
                    break;

                case VALIDACION:
                    validarCodigo();
                    break;

                case RECUPERACION:
                    restablecerContrasena();
                    break;
            }
        });

        btnAtras.setOnClickListener(v -> {
            if (isLoading) return;

            if (flowManager.esPrimerPaso()) {
                finish();
            } else {
                flowManager.anteriorPaso();
                actualizarFlipper();
            }
        });

        txtReenviarCodigo.setOnClickListener(v -> {
            if (!isLoading) reenviarCodigo();
        });
    }

    // ===================== UI STATE =====================

    private void setLoading(boolean loading) {
        isLoading = loading;
        btnSiguiente.setEnabled(!loading);
        btnSiguiente.setText(loading ? "Procesando..." : "Siguiente");
    }

    private void mostrarMensaje(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // ===================== PASO 1 - EMAIL =====================

    private void verificarCorreo() {

        if (!Validator.with(edtCorreo)
                .required("El correo es obligatorio")
                .email()
                .validate()) return;

        setLoading(true);

        String correo = edtCorreo.getText().toString().trim();

        viewModel.verificarCorreo(correo).observe(this, response -> {
            setLoading(false);

            if (!response.isSuccess()) {
                edtCorreo.setError(response.getMessage());
                return;
            }

            enviarCodigo(true); // SOLO AQUÍ AVANZA
        });
    }

    // ===================== PASO 2 - CÓDIGO =====================

    private void enviarCodigo(boolean avanzar) {

        setLoading(true);

        String correo = edtCorreo.getText().toString().trim();

        viewModel.enviarCodigo(correo).observe(this, response -> {
            setLoading(false);

            if (!response.isSuccess() || response.getData() == null) {
                mostrarMensaje(response.getMessage());
                return;
            }

            codigoBackend = response.getData().getCodigo();
            codigoValidado = false;
            edtCodigo.setText("");

            mostrarMensaje("Código enviado al correo");

            if (avanzar) {
                flowManager.siguientePaso();
                actualizarFlipper();
            }
        });
    }

    private void reenviarCodigo() {
        codigoBackend = 0;
        codigoValidado = false;
        edtCodigo.setText("");
        enviarCodigo(false); // ❌ NO AVANZA
    }

    private void validarCodigo() {

        if (!Validator.with(edtCodigo)
                .required("El código es obligatorio")
                .numeric()
                .length(6, "Debe tener 6 dígitos")
                .validate()) return;

        int codigoIngresado = Integer.parseInt(
                edtCodigo.getText().toString().trim()
        );

        if (codigoIngresado != codigoBackend) {
            edtCodigo.setError("Código incorrecto");
            edtCodigo.requestFocus();
            return;
        }

        codigoValidado = true;
        flowManager.siguientePaso();
        actualizarFlipper();
    }

    // ===================== PASO 3 - CONTRASEÑA =====================

    private void restablecerContrasena() {

        if (!Validator.with(edtContrasena)
                .required("La contraseña es obligatoria")
                .min(4, "Mínimo 4 caracteres")
                .validate()) return;

        if (!Validator.with(edtConfirmarContrasena)
                .required("Confirme la contraseña")
                .validate()) return;

        String pass = edtContrasena.getText().toString().trim();
        String confirm = edtConfirmarContrasena.getText().toString().trim();

        if (!pass.equals(confirm)) {
            edtConfirmarContrasena.setError("Las contraseñas no coinciden");
            edtConfirmarContrasena.requestFocus();
            return;
        }

        setLoading(true);

        String correo = edtCorreo.getText().toString().trim();

        viewModel.restablecerContrasena(correo, pass).observe(this, response -> {
            setLoading(false);
            mostrarMensaje(response.getMessage());

            if (response.isSuccess()) {
                limpiarEstado();
                finish();
            }
        });
    }

    // ===================== FLIPPER =====================

    private void actualizarFlipper() {
        flipper.setDisplayedChild(flowManager.getPasoActual().ordinal());
    }

    private void limpiarEstado() {
        codigoBackend = 0;
        codigoValidado = false;
        isLoading = false;
    }
}
