package com.RodCarvalhoas.BackeryStock.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class Requesicao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String descricao;

    @OneToOne
    private Usuario Usuario;

    @OneToMany
    private List<ItemReq> listaDeItemReq = new ArrayList<>();

}
