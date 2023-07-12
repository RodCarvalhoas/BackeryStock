package com.RodCarvalhoas.BackeryStock.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Campo NAME é requerido")
    @Length(min = 3, max = 100, message = "O campo NAME deve ter entre 3 e 100 caracteres")
    private String name;
    @NotEmpty(message = "Campo DESCRIÇÃO é requerido")
    @Length(min = 3, max = 200, message = "O campo DESCRIÇÃO deve ter entre 3 e 200 caracteres")
    private String descricao;

    @OneToMany(mappedBy = "categoria")
    private List<Item> items = new ArrayList<>();

    public Categoria(Integer id, String name, String descricao) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
    }
}
