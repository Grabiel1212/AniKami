package com.demo.presentation.activitys.recovery;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.demo.R;
import com.demo.presentation.activitys.recovery.components.RecoveryFlowManager;
import com.demo.presentation.activitys.recovery.components.RegistroPasosRecovery;

public class RecoveryActivity extends AppCompatActivity {


    private Button btn_siguiente_recovery , btn_atras_registrar;
    private ViewFlipper flipper;

    private RecoveryFlowManager flowManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recovery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        initEventos();
    }

    private void initViews() {
        btn_siguiente_recovery = findViewById(R.id.btn_siguiente_recovery);
        btn_atras_registrar = findViewById(R.id.btn_atras_recovery);
        flipper = findViewById(R.id.flipper_recovery);

        flowManager = new RecoveryFlowManager();
    }

    private void initEventos() {

        btn_siguiente_recovery.setOnClickListener(v -> {
            flowManager.siguientePaso();
            actualizarFlipper();
        });

        btn_atras_registrar.setOnClickListener(v -> {
            if (flowManager.esPrimerPaso()) {
                finish(); // si es primer paso, cierra
            } else {
                flowManager.anteriorPaso();
                actualizarFlipper();
            }
        });
    }

    private void actualizarFlipper() {
        RegistroPasosRecovery paso = flowManager.getPasoActual();

        switch (paso) {
            case EMAIL:
                flipper.setDisplayedChild(0);
                break;

            case VALIDACION:
                flipper.setDisplayedChild(1);
                break;

            case RECUPERACION:
                flipper.setDisplayedChild(2);
                break;
        }
    }
}