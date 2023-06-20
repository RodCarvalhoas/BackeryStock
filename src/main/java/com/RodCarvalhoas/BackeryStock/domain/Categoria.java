package com.RodCarvalhoas.BackeryStock.domain;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Categoria {

    private int id;
    private String name;
    private String descricao;

    private List<Item> items;

    public Categoria(int id, String name, String descricao) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
    }
}
