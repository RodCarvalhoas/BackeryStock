package com.RodCarvalhoas.BackeryStock.dtos;

import com.RodCarvalhoas.BackeryStock.Enums.UnMedida;
import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.domain.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class ItemDTO {

    private Integer id;

    @NotEmpty(message = "Campo NAME é requerido")
    @Length(min = 3, max = 100, message = "O campo NAME deve ter entre 3 e 100 caracteres")
    private String name;

    @NotNull(message = "Campo QUANTIDADE é requerido")
    @Min(value = 0, message = "A quantidade do item deve ser maior que 0")
    private Integer quantidade;

    @NotNull(message = "Campo VALOR_UN é requerido")
    @Min(value = 0, message = "O campo VALOR_UN deve ser maior que 0")
    private Double valorUn;

    @NotNull(message = "Campo UN_MEDIDA é requerido")
    @Enumerated(EnumType.STRING)
    private UnMedida unMedida;

    private Double total;

    @JsonIgnore
    private Categoria categoria;

    public ItemDTO(Integer id, String name, Integer quantidade, Double valorUn, UnMedida unMedida, Categoria categoria) {
        this.id = id;
        this.name = name;
        this.quantidade = quantidade;
        this.valorUn = valorUn;
        this.unMedida = unMedida;
        this.categoria = categoria;
    }

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
