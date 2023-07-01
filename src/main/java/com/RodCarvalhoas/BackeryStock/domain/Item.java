package com.RodCarvalhoas.BackeryStock.domain;

import com.RodCarvalhoas.BackeryStock.Enums.UnMedida;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer quantidade;
    private Double valor;
    private UnMedida unMedida;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}
