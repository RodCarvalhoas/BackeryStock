package com.RodCarvalhoas.BackeryStock.domain;

import com.RodCarvalhoas.BackeryStock.Enums.UnMedida;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer quantidade;
    private Double valorUn;
    @Enumerated(EnumType.STRING)
    private UnMedida unMedida;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Item(String name, Integer quantidade, Double valorUn, UnMedida unMedida, Categoria categoria) {
        this.name = name;
        this.quantidade = quantidade;
        this.valorUn = valorUn;
        this.unMedida = unMedida;
        this.categoria = categoria;
        calculateTotal();
    }

    @PostLoad
    public void calculateTotal() {
        total = quantidade * valorUn;
    }

}
