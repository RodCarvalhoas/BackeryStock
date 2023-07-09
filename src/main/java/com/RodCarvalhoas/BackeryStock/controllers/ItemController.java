package com.RodCarvalhoas.BackeryStock.controllers;

import com.RodCarvalhoas.BackeryStock.domain.Item;
import com.RodCarvalhoas.BackeryStock.dtos.ItemDTO;
import com.RodCarvalhoas.BackeryStock.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemDTO> findById(@PathVariable Integer id){
        Item item = itemService.findById(id);
        ItemDTO itemDTO = new ItemDTO(item);
        return ResponseEntity.ok().body(itemDTO);
    }

}
