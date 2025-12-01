package com.example.demo.CONTROLLERS;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.SERVICES.UsuarioServices;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControllers {
    
    @Autowired
    private UsuarioServices usuarioServices;

    @PostMapping("/verificar/email")
public ResponseEntity<?> VerificarEmail(@RequestBody Map<String, String> body) {
    String email = body.get("correo");

    if (email == null || email.isEmpty()) {
        return ResponseEntity.badRequest().body("El campo 'correo' es obligatorio.");
    }

    boolean existe = usuarioServices.verCorreo(email);

    String mensaje = existe
            ? "El email ya está en uso."
            : "El email está disponible.";

    return ResponseEntity.ok(mensaje);
}
}
