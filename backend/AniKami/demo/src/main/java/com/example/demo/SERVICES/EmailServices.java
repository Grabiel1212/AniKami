package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.helpers.ApiResponse;

@Service
public class EmailServices {

    @Autowired
    private JavaMailSender mailSender;

    public ApiResponse<Map<String, Object>> enviarCodigo(String email) {

        if (email == null || email.isBlank()) {
            return ApiResponse.error("El correo no puede estar vacío.");
        }

        // Generar código aleatorio de 6 dígitos
        int codigo = new Random().nextInt(900000) + 100000;

        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom("citamatchpasionlove@gmail.com");
            mensaje.setTo(email);
            mensaje.setSubject("Código de Verificación");
            mensaje.setText("Tu código de verificación es: " + codigo);

            mailSender.send(mensaje);

            // Data de respuesta
            Map<String, Object> data = new HashMap<>();
            data.put("codigo", codigo);

            return ApiResponse.success("Código enviado correctamente", data);

        } catch (Exception e) {
            return ApiResponse.error("Error al enviar el correo: " + e.getMessage());
        }
    }
}
