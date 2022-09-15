package com.UdeA.Ciclo3.service;

import com.UdeA.Ciclo3.modelos.Usuario;
import com.UdeA.Ciclo3.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuario( ) {
        List<Usuario> usuariosList = new ArrayList<>();
        usuarioRepository.findAll().forEach(usuario -> usuariosList.add(usuario));
        return usuariosList;
    }


    //busacar por usuario
    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    //buscar por empresa
    public ArrayList<Usuario> obtenerPorEmpresa(Integer id) {
        return usuarioRepository.findByEmpresa(id);
    }

    //Guardar Usuario
    public Usuario saveOrUpdateUsuario( Usuario usuario ) {
        return usuarioRepository.save(usuario);
    }

    //Eliminar usuario
    public boolean deleteUsuario( Integer id ) {
        usuarioRepository.deleteById(id);
        if (this.usuarioRepository.findById(id).isPresent()){
            return false;
        }
        return true;
    }
}
