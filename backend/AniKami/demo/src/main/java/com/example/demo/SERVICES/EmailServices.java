package com.example.demo.SERVICES;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.helpers.ApiResponse;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServices {

    @Autowired
    private JavaMailSender mailSender;

    public ApiResponse<Map<String, Object>> enviarCodigo(String email) {

        if (email == null || email.isBlank()) {
            return ApiResponse.error("El correo no puede estar vacío.");
        }

        int codigo = new Random().nextInt(900000) + 100000;

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom("citamatchpasionlove@gmail.com");
            helper.setTo(email);
            helper.setSubject("🔐 Código de Verificación");

            String html = """
                        <div style="font-family: Arial, sans-serif; background:#f4f6f8; padding:30px;">
                          <div style="max-width:500px; margin:auto; background:#ffffff; padding:25px; border-radius:10px; box-shadow:0 0 10px rgba(0,0,0,.1);">

                            <div style="text-align:center;">
                              <img src="https://res.cloudinary.com/dcolydznr/image/upload/v1766598792/animaki_s1pwgg.png"
                                   style="width:120px; margin-bottom:20px;" />
                            </div>

                            <h2 style="color:#333; text-align:center;">
                              Código de Verificación
                            </h2>

                            <p style="font-size:15px; color:#555; text-align:center;">
                              Usa el siguiente código para continuar:
                            </p>

                            <div style="font-size:32px; font-weight:bold; letter-spacing:6px;
                                        text-align:center; color:#6c63ff; margin:20px 0;">
                              %d
                            </div>

                            <p style="font-size:13px; color:#999; text-align:center;">
                              Este código es válido por unos minutos.
                            </p>

                            <hr style="margin:30px 0;">

                            <p style="font-size:12px; color:#aaa; text-align:center;">
                              © 2025 AniMaki · Todos los derechos reservados
                            </p>

                          </div>
                        </div>
                    """
                    .formatted(codigo);

            helper.setText(html, true);

            mailSender.send(mensaje);

            Map<String, Object> data = new HashMap<>();
            data.put("codigo", codigo);

            return ApiResponse.success("Correo enviado correctamente", data);

        } catch (MessagingException e) {
            return ApiResponse.error("Error al enviar correo: " + e.getMessage());
        }
    }
}
