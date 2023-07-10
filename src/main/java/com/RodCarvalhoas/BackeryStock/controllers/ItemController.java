package com.RodCarvalhoas.BackeryStock.controllers;

import com.RodCarvalhoas.BackeryStock.domain.Item;
import com.RodCarvalhoas.BackeryStock.dtos.ItemDTO;
import com.RodCarvalhoas.BackeryStock.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<ItemDTO>> findAll(@RequestParam(value = "categoria_id", defaultValue = "0")Integer id_cat){
        List<Item> itemList = itemService.findAllByCategoria(id_cat);
        List<ItemDTO> itemDTOList = new ArrayList<>();
        itemList.stream().map(item -> itemDTOList.add(new ItemDTO(item))).collect(Collectors.toList());
        return ResponseEntity.ok().body(itemDTOList);
    }

    @PutMapping(value = "/{idItemAntigo}")
    public ResponseEntity<ItemDTO> update(@PathVariable Integer idItemAntigo, @RequestBody Item itemAtualizado,
                                          @RequestParam (value = "categoria_id", defaultValue = "0")Integer id_cat){
        Item it = itemService.update(idItemAntigo, itemAtualizado, id_cat);
        return ResponseEntity.ok().body(new ItemDTO(it));
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item, @RequestParam (value = "categoria_id", defaultValue = "0")Integer id_cat){
        Item it = itemService.create(item, id_cat);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(it.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
