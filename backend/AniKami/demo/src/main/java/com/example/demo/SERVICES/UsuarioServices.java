package com.example.demo.SERVICES;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.HELPERS.ResponseHelper;
import com.example.demo.Model.Usuarios;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioServices {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private cloudinaryServices cloudinaryServices;

    public boolean verCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public Usuarios obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Map<String, Object> guardarUsuario(Usuarios usuario, MultipartFile archivo) {
        try {
            if (archivo != null && !archivo.isEmpty()) {
                System.out.println("Archivo recibido para el usuario: " + archivo.getOriginalFilename());

                Map<String, Object> resultadoSubida = cloudinaryServices.subirImagen(archivo);

                // Validación segura
                if (!Boolean.TRUE.equals(resultadoSubida.get("success"))) {
                    return ResponseHelper.enviarRespuesta(false, "Error al subir la imagen");
                }

                usuario.setFoto((String) resultadoSubida.get("url"));
                System.out.println("Imagen subida con éxito. URL: " + usuario.getFoto());
            } else {
                System.out.println("No se proporcionó ningún archivo para el usuario.");
            }

            usuarioRepository.save(usuario);
            return ResponseHelper.enviarRespuesta(true, "Usuario guardado exitosamente");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHelper.enviarRespuesta(false, "Error al guardar el usuario: " + e.getMessage());
        }
    }

}
