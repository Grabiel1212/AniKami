package com.example.demo.SERVICES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioServices {
    @Autowired
     private UsuarioRepository usuarioRepository;


     public boolean verCorreo(String correo) {
    return usuarioRepository.existsByCorreo(correo);
}


}
