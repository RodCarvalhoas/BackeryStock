package com.RodCarvalhoas.BackeryStock.dtos;

import com.RodCarvalhoas.BackeryStock.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
public class UsuarioRequest {

    private Long id;

    @NotEmpty(message = "Campo NAME é requerido")
    @Length(min = 3, max = 100, message = "O campo NAME deve ter entre 3 e 100 caracteres")
    private String name;

    @NotEmpty(message = "Campo EMAIL é requerido")
    @Email(message = "O campo EMAIL deve ser um endereço de e-mail válido")
    private String email;

    @NotEmpty(message = "Campo CPF é requerido")
    @Length(min = 11, max = 11, message = "O campo CPF deve ter 11 caracteres")
    @CPF(message = "Informe um cpf válido")
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;

}
