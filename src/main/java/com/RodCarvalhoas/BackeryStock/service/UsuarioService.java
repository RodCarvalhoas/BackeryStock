package com.RodCarvalhoas.BackeryStock.service;

import com.RodCarvalhoas.BackeryStock.domain.Usuario;
import com.RodCarvalhoas.BackeryStock.exceptions.DataIntegrityViolationException;
import com.RodCarvalhoas.BackeryStock.exceptions.ObjectNotFoundException;
import com.RodCarvalhoas.BackeryStock.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {

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
        if(usuarioRepository.findByName(usuario.getName()) != null){
            throw new DataIntegrityViolationException("Nome já está em uso");
        }
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            throw new DataIntegrityViolationException("Email já está em uso");
        }
        if(usuarioRepository.findBycpf(usuario.getCpf()).isPresent()){
            throw new DataIntegrityViolationException(("CPF já está em uso"));
        }

        String encrypyedPassword = new BCryptPasswordEncoder().encode(usuario.getPassword());
        Usuario newUsuario = new Usuario(null, usuario.getName(), usuario.getEmail(), usuario.getCpf(), usuario.getRole(),encrypyedPassword);

        newUsuario.setId(null);
        return usuarioRepository.save(newUsuario);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByName(username);
    }
}
