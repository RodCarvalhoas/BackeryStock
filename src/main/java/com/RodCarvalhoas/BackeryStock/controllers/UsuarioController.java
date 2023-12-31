package com.RodCarvalhoas.BackeryStock.controllers;

import com.RodCarvalhoas.BackeryStock.domain.Usuario;
import com.RodCarvalhoas.BackeryStock.dtos.UsuarioRequest;
import com.RodCarvalhoas.BackeryStock.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/Usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){
        Usuario Usuario = usuarioService.findById(id);
        return ResponseEntity.ok().body(Usuario);
    }

    @GetMapping(value = "findName")
    public ResponseEntity<Usuario> findByName(@RequestParam String name){
        Usuario Usuario = usuarioService.findByName(name);
        return ResponseEntity.ok().body(Usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll(){
        return ResponseEntity.ok().body(usuarioService.findAll());
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario Usuario){
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(usuarioService.create(Usuario).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id,@Valid @RequestBody UsuarioRequest Usuario){
        Usuario us = usuarioService.update(id, Usuario);
        return ResponseEntity.ok().body(us);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Usuario> partialUpdate(@PathVariable Long id, @Valid @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioService.partialUpdate(id, usuarioAtualizado);
        return ResponseEntity.ok().body(usuario);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
