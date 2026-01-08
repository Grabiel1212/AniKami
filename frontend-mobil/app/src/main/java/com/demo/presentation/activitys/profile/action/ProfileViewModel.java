package com.demo.presentation.activitys.profile.action;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.demo.data.common.BaseResponse;
import com.demo.data.model.Genero;
import com.demo.data.model.Usuario;
import com.demo.data.repocitory.GeneroRepository;
import com.demo.data.repocitory.UsuarioRepository;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileViewModel extends ViewModel {

    private final UsuarioRepository usuarioRepository;
    private final GeneroRepository generoRepository;

    public ProfileViewModel() {
        usuarioRepository = new UsuarioRepository();
        generoRepository = new GeneroRepository();
    }

    /* ===== INFO USUARIO ===== */
    public LiveData<BaseResponse<Usuario>> verInfoUsuario(int idUsuario) {
        return usuarioRepository.verInfoUsuario(idUsuario);
    }

    /* ===== EDITAR PERFIL ===== */
    public LiveData<BaseResponse<Usuario>> editarUsuario(
            RequestBody idUsuario,
            RequestBody nombre,
            RequestBody apellido,
            MultipartBody.Part foto
    ) {
        return usuarioRepository.editarUsuario(
                idUsuario,
                nombre,
                apellido,
                foto
        );
    }

    /* ===== PREFERENCIAS ===== */
    public LiveData<BaseResponse<List<Genero>>> obtenerGeneros(int idUsuario) {
        return generoRepository.obtenerGenerosPreferidos(idUsuario);
    }
}
