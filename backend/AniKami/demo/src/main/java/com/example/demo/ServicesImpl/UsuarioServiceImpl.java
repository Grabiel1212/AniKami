package com.example.demo.ServicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Usuarios;
import com.example.demo.SERVICES.UsuarioServices.UsuarioService;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

     @Autowired
    private UsuarioRepository repository;


    // ======================
    // listar usuarios
    // ======================
    @Override
    public List<Usuarios> ListarUsuario() {
        return repository.findAll();
    }

    // ======================
    // guardar usuario
    // ======================
    @Override
    public Usuarios guardar(Usuarios usuario) {
        return repository.save(usuario);
    }


    @Override
    public Usuarios actualizar(Integer id, Usuarios usuario) {
        Usuarios u = repository.findById(id).orElseThrow();
        u.setNombreUsuario(usuario.getNombreUsuario());
        u.setApellido(usuario.getApellido());
        u.setCorreo(usuario.getCorreo());
        u.setContrasena(usuario.getContrasena());
        u.setGoogleId(usuario.getGoogleId());
        u.setFoto(usuario.getFoto());
        return repository.save(u);
    }

    @Override
    public void eliminar(Integer idUsuario) {
        repository.deleteById(idUsuario);
    }
    
}
