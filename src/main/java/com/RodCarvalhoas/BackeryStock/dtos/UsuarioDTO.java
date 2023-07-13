package com.RodCarvalhoas.BackeryStock.dtos;

import com.RodCarvalhoas.BackeryStock.Enums.TypeUsuario;
import com.RodCarvalhoas.BackeryStock.domain.Usuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;

    @NotEmpty(message = "Campo NAME é requerido")
    @Length(min = 3, max = 100, message = "O campo NAME deve ter entre 3 e 100 caracteres")
    private String name;

    @Email
    private String email;

    @CPF
    private String cpf;

    @NotNull(message = "Campo TYPE_Usuario é requerido")
    @Enumerated(EnumType.STRING)
    private TypeUsuario typeUsuario;

    @NotEmpty(message = "Campo PASSWORD é requerido")
    @Length(min = 5, max = 100, message = "O campo PASSWORD deve ter entre 5 e 100 caracteres")
    private String password;

    public UsuarioDTO(Usuario Usuario) {
        this.id = Usuario.getId();
        this.name = Usuario.getName();
        this.email = Usuario.getEmail();
        this.cpf = Usuario.getCpf();
        this.typeUsuario = Usuario.getTypeUsuario();
        this.password = Usuario.getPassword();
    }
}
