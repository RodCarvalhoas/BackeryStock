package com.RodCarvalhoas.BackeryStock.controllers;

import com.RodCarvalhoas.BackeryStock.domain.Item;
import com.RodCarvalhoas.BackeryStock.dtos.ItemDTO;
import com.RodCarvalhoas.BackeryStock.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<ItemDTO> update(@PathVariable Integer idItemAntigo, @Valid @RequestBody Item itemAtualizado,
                                          @RequestParam (value = "categoria_id", defaultValue = "0")Integer id_cat){
        Item it = itemService.update(idItemAntigo, itemAtualizado, id_cat);
        return ResponseEntity.ok().body(new ItemDTO(it));
    }

    @PostMapping
    public ResponseEntity<Item> create(@Valid @RequestBody Item item, @RequestParam (value = "categoria_id", defaultValue = "0")Integer id_cat){
        Item it = itemService.create(item, id_cat);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(it.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        itemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/item-output")
    public ResponseEntity<ItemDTO> itemOutput (@PathVariable Integer id, @RequestBody Map<String, Integer> requestBody) {
        Integer quantidade = requestBody.get("quantidade");
        Item itemAtualizado = itemService.itemOutput(id, quantidade);
        ItemDTO itemDTO = new ItemDTO(itemAtualizado);
        return ResponseEntity.ok().body(itemDTO);
    }

    @PatchMapping("{id}/entry-item")
    public ResponseEntity<ItemDTO> entryItem(@PathVariable Integer id, @RequestBody Map<String, Integer> requestBody){
        Integer quantidade = requestBody.get("quantidade");
        Item itemAtualizado = itemService.entryItem(id, quantidade);
        ItemDTO itemDTO = new ItemDTO(itemAtualizado);
        return ResponseEntity.ok().body(itemDTO);
    }


}
