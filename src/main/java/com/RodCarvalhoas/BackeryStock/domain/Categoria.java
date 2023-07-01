package com.RodCarvalhoas.BackeryStock.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String descricao;

    @OneToMany(mappedBy = "categoria")
    private List<Item> items = new ArrayList<>();

    public Categoria(Integer id, String name, String descricao) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
    }
}
