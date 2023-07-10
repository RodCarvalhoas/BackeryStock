package com.RodCarvalhoas.BackeryStock.service;

import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.domain.Item;
import com.RodCarvalhoas.BackeryStock.exceptions.ObjectNotFoundException;
import com.RodCarvalhoas.BackeryStock.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoriaService categoriaService;

    public Item findById(Integer id){
        Optional<Item> item = itemRepository.findById(id);
        if(item.isEmpty()){
            throw new ObjectNotFoundException("Item com o id: " + id + " não pode ser encontrado.");
        }
        return item.get();
    }

    public List<Item> findAllByCategoria(Integer id_cat){
        categoriaService.findById(id_cat);
        return itemRepository.findAllByCategoria(id_cat);
    }


    public Item update(Integer idItemAntigo, Item itemAtualizado, Integer id_cat) {
        Categoria cat = categoriaService.findById(id_cat);
        Item itemAntigo = findById(idItemAntigo);
        itemAtualizado.setId(itemAntigo.getId());
        itemAtualizado.setCategoria(cat);
        itemAtualizado.setTotal(calcularValorTotal(itemAtualizado));
        return itemRepository.save(itemAtualizado);
    }

    private Double calcularValorTotal(Item item){
        return item.getQuantidade() * item.getValorUn();
    }

}
