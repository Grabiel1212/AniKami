package com.example.demo.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.helpers.ApiResponse;
import com.example.demo.model.UsuarioPreferenciaGenero;
import com.example.demo.model.Usuarios;
import com.example.demo.repository.UsuarioPreferenciaGeneroRepository;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private cloudinaryServices cloudinaryServices;

    @Autowired
    private UsuarioPreferenciaGeneroRepository usuarioPreferenciaGeneroRepository;

    // ========================
    // VERIFICAR CORREO EXISTE
    // ========================
    public boolean verificarCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    // ==========================================
    // VERIFICAR SI EL CORREO ES GOOGLE O NORMAL
    // ==========================================
    public ApiResponse<Boolean> verificarCorreoParaRecuperar(String correo) {

        Usuarios usuario = usuarioRepository.findByCorreo(correo);

        // ❌ Caso A: No existe
        if (usuario == null) {
            return ApiResponse.error(
                    "El correo no está registrado.",
                    null);
        }

        // ❌ Caso B: Tiene GoogleID → no puede recuperar contraseña
        if (usuario.getGoogleId() != null) {
            return ApiResponse.error(
                    "Este correo pertenece a una cuenta Google. No puede recuperar contraseña.",
                    null);
        }

        // ❌ Caso C: Existe pero NO tiene contraseña
        if (usuario.getContrasena() == null || usuario.getContrasena().isBlank()) {
            return ApiResponse.error(
                    "El correo no tiene contraseña registrada.",
                    null);
        }

        // ✔ Caso OK — Existente, no Google, con contraseña
        return ApiResponse.success(
                "Correo válido para recuperación de contraseña.",
                true);
    }

    // ======================
    // REGISTRO NORMAL
    // ======================
    public ApiResponse<Usuarios> guardarUsuario(Usuarios usuario, MultipartFile archivo,
            List<Integer> generosFavoritos) {
        try {

            // Subir foto si existe
            if (archivo != null && !archivo.isEmpty()) {
                Map<String, Object> resultadoSubida = cloudinaryServices.subirImagen(archivo);

                if (!Boolean.TRUE.equals(resultadoSubida.get("success"))) {
                    return ApiResponse.error("Error al subir la imagen");
                }

                usuario.setFoto((String) resultadoSubida.get("url"));
            }

            // Guardar usuario
            Usuarios guardado = usuarioRepository.save(usuario);

            // Guardar géneros favoritos si existen
            if (generosFavoritos != null && !generosFavoritos.isEmpty()) {
                for (Integer idGenero : generosFavoritos) {
                    UsuarioPreferenciaGenero pref = new UsuarioPreferenciaGenero();
                    pref.setUsuarioId(guardado.getIdUsuario());
                    pref.setGeneroId(idGenero);
                    usuarioPreferenciaGeneroRepository.save(pref);
                }
            }

            return ApiResponse.success("Usuario guardado exitosamente", guardado);

        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("Error al guardar el usuario: " + e.getMessage());
        }
    }

    // ======================
    // REGISTRO GOOGLE
    // ======================
    public ApiResponse<Usuarios> guardarUsuarioGoogle(Usuarios usuario, MultipartFile archivo,
            List<Integer> generosFavoritos) {
        try {

            // Subir foto si existe
            if (archivo != null && !archivo.isEmpty()) {
                Map<String, Object> resultadoSubida = cloudinaryServices.subirImagen(archivo);

                if (!Boolean.TRUE.equals(resultadoSubida.get("success"))) {
                    return ApiResponse.error("Error al subir la imagen");
                }

                usuario.setFoto((String) resultadoSubida.get("url"));
            }

            // Guardar usuario
            Usuarios guardado = usuarioRepository.save(usuario);

            // Guardar géneros favoritos si existen
            if (generosFavoritos != null && !generosFavoritos.isEmpty()) {
                for (Integer idGenero : generosFavoritos) {
                    UsuarioPreferenciaGenero pref = new UsuarioPreferenciaGenero();
                    pref.setUsuarioId(guardado.getIdUsuario());
                    pref.setGeneroId(idGenero);
                    usuarioPreferenciaGeneroRepository.save(pref);
                }
            }

            return ApiResponse.success("Usuario Google guardado exitosamente", guardado);

        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("Error al guardar usuario Google: " + e.getMessage());
        }
    }

    // ==============================
    // LOGIN CORREO + CONTRASEÑA
    // ==============================
    public ApiResponse<Object> loginCorreo(String correo, String contrasena) {

        Usuarios usuario = usuarioRepository.findByCorreo(correo);

        // ❌ No existe
        if (usuario == null) {
            return ApiResponse.error("Este correo no está registrado.");
        }

        // ❌ Es Google → no puede iniciar con contraseña
        if (usuario.getGoogleId() != null) {
            return ApiResponse.error("Este correo pertenece a una cuenta Google. Inicie sesión con Google.");
        }

        // ❌ Contraseña incorrecta
        if (!usuario.getContrasena().equals(contrasena)) {
            return ApiResponse.error("Credenciales incorrectas.");
        }

        // ✔ Correcto
        return ApiResponse.success(
                "Inicio de sesión exitoso.",
                Map.of(
                        "login", true,
                        "usuario", usuario));
    }

    // ==============================
    // LOGIN GOOGLE
    // ==============================
    public ApiResponse<Object> loginGoogle(String googleId) {

        Usuarios usuario = usuarioRepository.findByGoogleId(googleId);

        if (usuario == null) {
            return ApiResponse.error(
                    "No existe una cuenta asociada a este GoogleID.",
                    Map.of("login", false));
        }

        return ApiResponse.success(
                "Inicio de sesión con Google exitoso.",
                Map.of(
                        "login", true,
                        "usuario", usuario));
    }

    public ApiResponse<Object> restablecerContrasena(String correo, String nuevaContra) {

        Usuarios usuario = usuarioRepository.findByCorreo(correo);

        // ❌ Caso A: No existe
        if (usuario == null) {
            return ApiResponse.error("El correo no está registrado.");
        }

        // ❌ Caso B: Tiene GoogleID → no puede restablecer
        if (usuario.getGoogleId() != null) {
            return ApiResponse.error("Esta cuenta usa Google. No puede restablecer contraseña.");
        }

        // ✔ Caso OK
        usuario.setContrasena(nuevaContra);
        usuarioRepository.save(usuario);

        return ApiResponse.success("Contraseña restablecida exitosamente.", null);
    }

}
