package com.RodCarvalhoas.BackeryStock.service;

import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.exceptions.ObjectNotFoundException;
import com.RodCarvalhoas.BackeryStock.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public Categoria findById(Integer id){
        Optional<Categoria> cat = categoriaRepository.findById(id);
        if(cat.isEmpty()){
            throw new ObjectNotFoundException("Categoria com o id: " + id +", não encontrado! \nTipo: " + Categoria.class.getName());
        }
        return cat.get();
    }

}
