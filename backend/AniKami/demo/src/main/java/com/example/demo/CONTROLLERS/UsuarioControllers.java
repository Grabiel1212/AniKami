package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.helpers.ApiResponse;
import com.example.demo.model.Usuarios;
import com.example.demo.services.UsuarioServices;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControllers {

        @Autowired
        private UsuarioServices usuarioServices;

        // ============================================================
        // VALIDAR QUE EL CORREO NO ESTÉ REGISTRADO (REGISTRO NORMAL)
        // ============================================================
        @PostMapping("/email-validacion")
        public ResponseEntity<ApiResponse<?>> validarRegistro(@RequestBody Map<String, String> body) {

                String email = body.get("correo");

                if (email == null || email.trim().isEmpty()) {
                        return ResponseEntity
                                        .badRequest()
                                        .body(ApiResponse.error("El campo 'correo' es obligatorio."));
                }

                boolean existe = usuarioServices.verificarCorreo(email.trim());

                if (existe) {
                        return ResponseEntity
                                        .badRequest()
                                        .body(ApiResponse.error("Este correo ya está registrado."));
                }

                return ResponseEntity.ok(
                                ApiResponse.success("Correo válido para registro.", null));
        }

        // ============================================================
        // VERIFICAR SI CORREO PERTENECE A GOOGLE O NORMAL
        // ============================================================
        @PostMapping("/verificar-recuperacion")
        public ResponseEntity<ApiResponse<?>> verificarCorreoRecuperacion(@RequestBody Map<String, String> body) {

                String correo = body.get("correo");

                if (correo == null || correo.isBlank()) {
                        return ResponseEntity
                                        .badRequest()
                                        .body(ApiResponse.error("El campo 'correo' es obligatorio."));
                }

                return ResponseEntity.ok(
                                usuarioServices.verificarCorreoParaRecuperar(correo.trim()));
        }

        // ============================================================
        // REGISTRO NORMAL (EMAIL + CONTRASEÑA)
        // ============================================================
        @PostMapping("/registrar-email")
        public ResponseEntity<ApiResponse<?>> agregarUsuarioEmail(
                        @RequestParam("NombreUsuario") String nombreUsuario,
                        @RequestParam("correo") String correo,
                        @RequestParam(value = "contrasena", required = false) String contrasena,
                        @RequestParam(value = "google_id", required = false) String google_id,
                        @RequestParam(value = "foto", required = false) MultipartFile foto,
                        @RequestParam(value = "generosFavoritos", required = false) List<Integer> generosFavoritos) {

                Usuarios usuario = Usuarios.builder()
                                .nombreUsuario(nombreUsuario)
                                .correo(correo)
                                .contrasena(contrasena)
                                .googleId(google_id)
                                .build();

                return ResponseEntity.ok(
                                usuarioServices.guardarUsuario(usuario, foto, generosFavoritos));
        }

        // ============================================================
        // REGISTRO GOOGLE
        // ============================================================
        @PostMapping("/registrar-google")
        public ResponseEntity<ApiResponse<?>> agregarUsuarioGoogle(
                        @RequestParam("NombreUsuario") String nombreUsuario,
                        @RequestParam("correo") String correo,
                        @RequestParam(value = "google_id", required = false) String google_id,
                        @RequestParam(value = "foto", required = false) MultipartFile foto,
                        @RequestParam(value = "generosFavoritos", required = false) List<Integer> generosFavoritos) {

                Usuarios usuario = Usuarios.builder()
                                .nombreUsuario(nombreUsuario)
                                .correo(correo)
                                .googleId(google_id)
                                .build();

                return ResponseEntity.ok(
                                usuarioServices.guardarUsuarioGoogle(usuario, foto, generosFavoritos));
        }

        // ==============================
        // LOGIN CORREO + CONTRASEÑA
        // ==============================
        @PostMapping("/login")
        public ResponseEntity<ApiResponse<?>> login(@RequestBody Map<String, String> body) {

                String correo = body.get("correo");
                String contrasena = body.get("contrasena");

                if (correo == null || correo.isBlank()) {
                        return ResponseEntity.badRequest().body(ApiResponse.error("El correo es obligatorio."));
                }
                if (contrasena == null || contrasena.isBlank()) {
                        return ResponseEntity.badRequest().body(ApiResponse.error("La contraseña es obligatoria."));
                }

                return ResponseEntity.ok(
                                usuarioServices.loginCorreo(correo.trim(), contrasena.trim()));
        }

        // ==============================
        // LOGIN GOOGLE
        // ==============================
        @PostMapping("/login-google")
        public ResponseEntity<ApiResponse<?>> loginGoogle(@RequestBody Map<String, String> body) {

                String googleId = body.get("google_id");

                if (googleId == null || googleId.isBlank()) {
                        return ResponseEntity.badRequest().body(ApiResponse.error("El GoogleID es obligatorio."));
                }

                return ResponseEntity.ok(usuarioServices.loginGoogle(googleId.trim()));
        }

        @PostMapping("/restablecer-contrasena")
        public ResponseEntity<ApiResponse<Object>> restablecerContrasena(
                        @RequestBody Map<String, String> request) {

                String correo = request.get("correo");
                String nuevaContra = request.get("nuevaContrasena");

                return ResponseEntity.ok(
                                usuarioServices.restablecerContrasena(correo, nuevaContra));
        }

}
