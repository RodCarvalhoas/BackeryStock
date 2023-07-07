package com.RodCarvalhoas.BackeryStock.dtos;

import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CategoriaDTO implements Serializable {

    private Integer id;
    private String name;
    private String descricao;

    public CategoriaDTO(Categoria cat) {
        this.id = cat.getId();
        this.name = cat.getName();
        this.descricao = cat.getDescricao();
    }
}
