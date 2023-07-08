package com.RodCarvalhoas.BackeryStock.controllers;

import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.dtos.CategoriaDTO;
import com.RodCarvalhoas.BackeryStock.service.CategoriaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> findById (@PathVariable Integer id){
        Categoria cat = categoriaService.findById(id);
        return ResponseEntity.ok().body(cat);
    }

    @GetMapping //Rertona uma lista de todas as categorias *Apenas categoria, sem os itens*
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        List<Categoria> categoriaList = categoriaService.findAll();
        List<CategoriaDTO> categoriaDTOList = categoriaList.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoriaDTOList);
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody CategoriaDTO categoriaDTO){
        Categoria cat = new Categoria();
        BeanUtils.copyProperties(categoriaDTO, cat);
        categoriaService.create(cat);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cat.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> update(@RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id){
        Categoria cat = categoriaService.update(categoriaDTO, id);
        return ResponseEntity.ok().body(new CategoriaDTO(cat));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
