package com.RodCarvalhoas.BackeryStock.repositories;

import com.RodCarvalhoas.BackeryStock.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Usuario findByName(String name);
    Optional<Usuario> findBycpf(String cpf);
    Optional<Usuario> findByEmail(String email);

}
