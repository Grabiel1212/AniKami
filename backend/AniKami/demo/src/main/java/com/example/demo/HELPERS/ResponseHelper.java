package com.example.demo.HELPERS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.Model.ApiResponse;

public class ResponseHelper {
    
    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(true, message, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        ApiResponse<T> response = new ApiResponse<>(false, message, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    public static Map<String, Object> enviarRespuesta(boolean exito, String mensaje) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("success", exito);
        respuesta.put("message", mensaje);
        respuesta.put("data", new ArrayList<>()); // Asegura que data siempre sea un array vacío
        return respuesta;
    }

    public static <T> Map<String, Object> enviarRespuesta(boolean exito, String mensaje, T datos) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("success", exito);
        respuesta.put("message", mensaje);
        respuesta.put("data", datos != null ? datos : new ArrayList<>()); // Si datos es null, devuelve un array vacío
        return respuesta;
    }
}
