package com.RodCarvalhoas.BackeryStock.domain;

import com.RodCarvalhoas.BackeryStock.Enums.TypeUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotNull(message = "Campo TYPE_Usuario é requerido")
    @Enumerated(EnumType.STRING)
    private TypeUsuario typeUsuario;

    @NotEmpty(message = "Campo PASSWORD é requerido")
    @Length(min = 5, max = 100, message = "O campo PASSWORD deve ter entre 5 e 100 caracteres")
    private String password;


}
