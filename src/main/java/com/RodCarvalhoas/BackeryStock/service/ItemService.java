package com.RodCarvalhoas.BackeryStock.service;

import com.RodCarvalhoas.BackeryStock.domain.Item;
import com.RodCarvalhoas.BackeryStock.exceptions.ObjectNotFoundException;
import com.RodCarvalhoas.BackeryStock.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item findById(Integer id){
        Optional<Item> item = itemRepository.findById(id);
        if(item.isEmpty()){
            throw new ObjectNotFoundException("Item com o id: " + id + " não pode ser encontrado.");
        }
        return item.get();
    }


}
