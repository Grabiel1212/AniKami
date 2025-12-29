package com.demo.presentation.activitys.recovery.action;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.demo.data.common.BaseResponse;
import com.demo.data.model.CodigoResponse;
import com.demo.data.repocitory.UsuarioRepository;
import com.demo.data.request.VerificacionRequest;


public class RecoveryViewModel extends ViewModel {

    private final UsuarioRepository repository = new UsuarioRepository();

    public LiveData<BaseResponse<Boolean>> verificarCorreo(String correo) {
        return repository.verificarEmailGoogle(
                new VerificacionRequest(correo)
        );
    }

    public LiveData<BaseResponse<CodigoResponse>> enviarCodigo(String correo) {
        return repository.enviarCodigoVerificacion(
                new VerificacionRequest(correo)
        );
    }

    public LiveData<BaseResponse<Void>> restablecerContrasena(
            String correo,
            String contrasena
    ) {
        return repository.restablecerContrasena(correo, contrasena);
    }
}
