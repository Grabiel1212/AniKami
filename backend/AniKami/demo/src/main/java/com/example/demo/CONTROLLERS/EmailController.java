package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.helpers.ApiResponse;
import com.example.demo.services.EmailServices;

@RestController
@RequestMapping("/correo")
public class EmailController {

    @Autowired
    private EmailServices emailService;

    @PostMapping("/enviar-codigo")
    public ResponseEntity<ApiResponse<Map<String, Object>>> enviarCodigo(
            @RequestBody Map<String, String> request) {

        String email = request.get("email");

        ApiResponse<Map<String, Object>> response = emailService.enviarCodigo(email);

        return ResponseEntity.ok(response);
    }
}
