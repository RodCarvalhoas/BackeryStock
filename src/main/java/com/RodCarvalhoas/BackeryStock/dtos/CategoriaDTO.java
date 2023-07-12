package com.RodCarvalhoas.BackeryStock.dtos;

import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CategoriaDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "Campo NAME é requerido")
    @Length(min = 3, max = 100, message = "O campo NAME deve ter entre 3 e 100 caracteres")
    private String name;

    @NotEmpty(message = "Campo DESCRIÇÃO é requerido")
    @Length(min = 3, max = 200, message = "O campo DESCRIÇÃO deve ter entre 3 e 200 caracteres")
    private String descricao;

    public CategoriaDTO(Categoria cat) {
        this.id = cat.getId();
        this.name = cat.getName();
        this.descricao = cat.getDescricao();
    }
}
