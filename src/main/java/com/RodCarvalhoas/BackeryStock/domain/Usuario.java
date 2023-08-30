package com.RodCarvalhoas.BackeryStock.domain;

import com.RodCarvalhoas.BackeryStock.Enums.Role;
import com.RodCarvalhoas.BackeryStock.config.security.CustomAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Campo NAME é requerido")
    @Length(min = 3, max = 100, message = "O campo NAME deve ter entre 3 e 100 caracteres")
    @Column(unique = true)
    private String name;

    @NotEmpty(message = "Campo EMAIL é requerido")
    @Email(message = "O campo EMAIL deve ser um endereço de e-mail válido")
    private String email;

    @NotEmpty(message = "Campo CPF é requerido")
    @Length(min = 11, max = 11, message = "O campo CPF deve ter 11 caracteres")
    @CPF(message = "Informe um cpf válido")
    @Column(unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotEmpty(message = "Campo PASSWORD é requerido")
    @Length(min = 5, max = 100, message = "O campo PASSWORD deve ter entre 5 e 100 caracteres")
    private String password;

    public Usuario(String name, Role role, String password) {
        this.name = name;
        this.role = role;
        this.password = password;
    }

    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Role.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.name;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
