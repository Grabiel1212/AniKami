package com.example.demo.CONTROLLERS;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.HELPERS.ResponseHelper;
import com.example.demo.Model.Usuarios;
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
            return ResponseHelper.badRequest("El campo 'correo' es obligatorio.");
        }

        boolean existe = usuarioServices.verCorreo(email);

        String mensaje = existe
                ? "El email ya está en uso."
                : "El email está disponible.";

        return ResponseHelper.success(mensaje, existe);
    }


    
    @PostMapping("/registrar/email-validacion")
    public ResponseEntity<?> validarRegistro(@RequestBody Map<String, String> body) {

    String email = body.get("correo");

    if (email == null || email.trim().isEmpty()) {
        return ResponseHelper.badRequest("El campo 'correo' es obligatorio.");
    }

    boolean existe = usuarioServices.verCorreo(email.trim());

    if (existe) {
        return ResponseHelper.badRequest("Este correo ya está registrado.");
    }

    return ResponseHelper.success("Correo válido para registro.", null);
}

    @PostMapping("/registrar-email")
    public ResponseEntity<Map<String , Object>> agregarUsuario(@RequestParam ("NombreUsuario") String NombreUsuario,
    @RequestParam ("correo")String correo,
    @RequestParam (value = "contrasena" ,required = false)String contrasena,
    @RequestParam (value = "google_id" , required = false) String google_id,
    @RequestParam (value = "foto" , required = false) MultipartFile foto
    )
    {
        Usuarios usuario = Usuarios.builder()
        .NombreUsuario(NombreUsuario)
        .correo(correo)
        .contrasena(contrasena)
        .google_id(google_id)
        .build();

        return ResponseEntity.ok(usuarioServices.guardarUsuario(usuario, foto));
    }
    


}
