package com.RodCarvalhoas.BackeryStock.service;

import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.domain.Item;
import com.RodCarvalhoas.BackeryStock.repositories.CategoriaRepository;
import com.RodCarvalhoas.BackeryStock.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.RodCarvalhoas.BackeryStock.Enums.UnMedida.KG;
import static com.RodCarvalhoas.BackeryStock.Enums.UnMedida.L;

@Service
public class DBservice {

    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ItemRepository itemRepository;

    public void instanciaBaseDeDados(){
        Categoria cat1 = new Categoria(null, "Farinhas & Grãos", "Farinhas e Grãos");
        Categoria cat2 = new Categoria(null, "Liquidos", "Liquidos e Derivados");

        Item it1 = new Item("Farinha de Trigo", 5, 2.0, KG, cat1);
        Item it2 = new Item("Mel", 2, 10.0, L, cat2);
        Item it3 = new Item("Leite", 20, 4.50, L,cat2);

        cat1.getItems().addAll(Arrays.asList(it1));
        cat2.getItems().addAll(Arrays.asList(it2,it3));

        categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
        itemRepository.saveAll(Arrays.asList(it1,it2,it3));

    }
}
