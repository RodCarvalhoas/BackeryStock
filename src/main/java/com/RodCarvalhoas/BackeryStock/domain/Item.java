package com.RodCarvalhoas.BackeryStock.domain;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Item {

    private int id;
    private String name;
    private Integer quantidade;
    private Double valor;

    private Categoria categoria;

}
