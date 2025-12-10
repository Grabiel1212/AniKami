package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
        boolean existsByCorreo(String correo);
        Usuarios findByCorreo(String correo);
}
