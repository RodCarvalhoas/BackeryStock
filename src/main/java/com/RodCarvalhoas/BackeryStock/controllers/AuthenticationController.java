package com.RodCarvalhoas.BackeryStock.controllers;

import com.RodCarvalhoas.BackeryStock.domain.Usuario;
import com.RodCarvalhoas.BackeryStock.dtos.AuthenticationDTO;
import com.RodCarvalhoas.BackeryStock.dtos.LoginResponseDTO;
import com.RodCarvalhoas.BackeryStock.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        var usuarioNamePassword = new UsernamePasswordAuthenticationToken(dto.getName(), dto.getPassword());
        var auth = this.authenticationManager.authenticate(usuarioNamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        var role = ((Usuario) auth.getPrincipal()).getRole();

        return ResponseEntity.ok(new LoginResponseDTO(token, role.getRole()));
    }
}
