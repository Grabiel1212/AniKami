package com.example.demo.SERVICES;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class cloudinaryServices {
    @Autowired
    private Cloudinary cloudinary;

    @SuppressWarnings("unchecked")
    public Map<String, Object> subirImagen(MultipartFile archivo) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            System.out.println("Iniciando subida a Cloudinary...");
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    archivo.getBytes(),
                    ObjectUtils.asMap("folder", "AniKami/usuarios"));

            String url = (String) uploadResult.get("secure_url");
            System.out.println("URL generada por Cloudinary: " + url);

            respuesta.put("success", true);
            respuesta.put("url", url);
        } catch (IOException e) {
            e.printStackTrace();
            respuesta.put("success", false);
            respuesta.put("message", "Error al subir la imagen: " + e.getMessage());
        }
        return respuesta;
    }

    public Map<String, Object> eliminarImagen(String url) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            if (url == null || url.isEmpty()) {
                respuesta.put("success", false);
                respuesta.put("message", "URL no válida");
                return respuesta;
            }

            // Extraer public_id desde la URL
            String publicId = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
            publicId = "AniKami/usuarios/" + publicId;

            Map<String, Object> deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            if ("ok".equals(deleteResult.get("result"))) {
                respuesta.put("success", true);
                respuesta.put("message", "Imagen eliminada correctamente");
            } else {
                respuesta.put("success", false);
                respuesta.put("message", "Error al eliminar la imagen en Cloudinary");
            }
        } catch (Exception e) {
            respuesta.put("success", false);
            respuesta.put("message", "Error al eliminar la imagen: " + e.getMessage());
        }
        return respuesta;
    }
}
