package com.RodCarvalhoas.BackeryStock.domain;

import com.RodCarvalhoas.BackeryStock.Enums.UnMedida;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "valor_total")
    private Double total;

    @JsonIgnore
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
