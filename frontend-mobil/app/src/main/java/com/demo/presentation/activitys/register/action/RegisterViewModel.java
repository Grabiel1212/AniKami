package com.demo.presentation.activitys.register.action;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.demo.data.common.BaseResponse;
import com.demo.data.model.Genero;
import com.demo.data.model.Usuario;
import com.demo.data.repocitory.UsuarioRepository;
import com.demo.data.repocitory.GeneroRepository;
import com.demo.data.request.VerificacionRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterViewModel extends ViewModel {

    private final UsuarioRepository usuarioRepository;
    private final GeneroRepository generoRepository;

    // 🔹 Datos temporales del registro
    private String correo;
    private List<Integer> generosSeleccionados = new ArrayList<>();

    public RegisterViewModel() {
        usuarioRepository = new UsuarioRepository();
        generoRepository = new GeneroRepository();
    }

    // =========================
    // 🔹 VERIFICAR CORREO
    // =========================
    public LiveData<BaseResponse<Boolean>> verificarCorreo(String correo) {
        this.correo = correo;
        return usuarioRepository.verificarEmail(
                new VerificacionRequest(correo)
        );
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    // =========================
    // 🔹 LISTAR GÉNEROS
    // =========================
    public LiveData<BaseResponse<List<Genero>>> listarGeneros() {
        return generoRepository.listarGeneros();
    }

    // =========================
    // 🔹 GÉNEROS SELECCIONADOS
    // =========================
    public void setGenerosSeleccionados(List<Integer> generosIds) {
        this.generosSeleccionados = generosIds;
    }

    public List<Integer> getGenerosSeleccionados() {
        return generosSeleccionados;
    }

    // =========================
    // 🔹 LIMPIAR (opcional)
    // =========================
    public void limpiarRegistro() {
        correo = null;
        generosSeleccionados.clear();
    }

    public LiveData<BaseResponse<Usuario>> registrarUsuarioEmail(
            String nombreUsuario,
            String apellido,
            String correo,
            String contrasena,
            File foto
    ) {

        RequestBody nombreRB = RequestBody.create(nombreUsuario, MediaType.parse("text/plain"));
        RequestBody apellidoRB = RequestBody.create(apellido, MediaType.parse("text/plain"));
        RequestBody correoRB = RequestBody.create(correo, MediaType.parse("text/plain"));
        RequestBody contrasenaRB = RequestBody.create(contrasena, MediaType.parse("text/plain"));

        RequestBody generosRB = RequestBody.create(
                convertirGeneros(),
                MediaType.parse("text/plain")
        );

        MultipartBody.Part fotoPart = crearFotoPart(foto);

        return usuarioRepository.registrarUsuarioEmail(
                nombreRB,
                apellidoRB,
                correoRB,
                contrasenaRB,
                generosRB,
                fotoPart
        );
    }

    public LiveData<BaseResponse<Usuario>> registrarUsuarioGoogle(
            String nombreUsuario,
            String apellido,
            String correo,
            String googleId,
            File foto
    ) {

        RequestBody nombreRB = RequestBody.create(nombreUsuario, MediaType.parse("text/plain"));
        RequestBody apellidoRB = RequestBody.create(apellido, MediaType.parse("text/plain"));
        RequestBody correoRB = RequestBody.create(correo, MediaType.parse("text/plain"));
        RequestBody googleRB = RequestBody.create(googleId, MediaType.parse("text/plain"));

        RequestBody generosRB = RequestBody.create(
                convertirGeneros(),
                MediaType.parse("text/plain")
        );

        MultipartBody.Part fotoPart = crearFotoPart(foto);

        return usuarioRepository.registrarUsuarioGoogle(
                nombreRB,
                apellidoRB,
                correoRB,
                googleRB,
                generosRB,
                fotoPart
        );
    }
    private String convertirGeneros() {
        if (generosSeleccionados == null || generosSeleccionados.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < generosSeleccionados.size(); i++) {
            sb.append(generosSeleccionados.get(i));
            if (i < generosSeleccionados.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    private MultipartBody.Part crearFotoPart(File foto) {
        if (foto == null) return null;

        RequestBody requestFile =
                RequestBody.create(foto, MediaType.parse("image/*"));

        return MultipartBody.Part.createFormData(
                "foto",
                foto.getName(),
                requestFile
        );
    }




}