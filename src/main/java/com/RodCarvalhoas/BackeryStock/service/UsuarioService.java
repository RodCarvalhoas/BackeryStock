package com.RodCarvalhoas.BackeryStock.service;

import com.RodCarvalhoas.BackeryStock.domain.Usuario;
import com.RodCarvalhoas.BackeryStock.exceptions.DataIntegrityViolationException;
import com.RodCarvalhoas.BackeryStock.exceptions.ObjectNotFoundException;
import com.RodCarvalhoas.BackeryStock.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findById(Long id){
        Optional<Usuario> Usuario = usuarioRepository.findById(id);
        return Usuario.orElseThrow(() -> new ObjectNotFoundException("Usuario com o Id: " + id + " não encontrado"));
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario create(Usuario usuario) {
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            throw new DataIntegrityViolationException("Email já está em uso");
        }
        if(usuarioRepository.findBycpf(usuario.getCpf()).isPresent()){
            throw new DataIntegrityViolationException(("CPF já está em uso"));
        }
        usuario.setId(null);
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario usuarioAtualizado) {
        Usuario Usuario = findById(id);
        usuarioAtualizado.setId(Usuario.getId());
        return usuarioRepository.save(usuarioAtualizado);
    }

    public void deleteById(Long id) {
        Usuario usuario = findById(id);
        usuarioRepository.deleteById(usuario.getId());
    }
}
