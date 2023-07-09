package com.RodCarvalhoas.BackeryStock.dtos;

import com.RodCarvalhoas.BackeryStock.Enums.UnMedida;
import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.domain.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDTO {

    private Integer id;
    private String name;
    private Integer quantidade;
    private Double valorUn;
    @Enumerated(EnumType.STRING)
    private UnMedida unMedida;
    private Double total;
    @JsonIgnore
    private Categoria categoria;

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.quantidade = item.getQuantidade();
        this.valorUn = item.getValorUn();
        this.unMedida = item.getUnMedida();
        this.total = item.getTotal();
        this.categoria = item.getCategoria();
    }
}
