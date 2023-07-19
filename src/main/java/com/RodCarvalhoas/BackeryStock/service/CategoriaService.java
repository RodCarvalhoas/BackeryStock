package com.RodCarvalhoas.BackeryStock.service;

import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.dtos.CategoriaDTO;
import com.RodCarvalhoas.BackeryStock.exceptions.DataIntegrityViolationException;
import com.RodCarvalhoas.BackeryStock.exceptions.ObjectNotFoundException;
import com.RodCarvalhoas.BackeryStock.repositories.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria findById(Integer id){
        Optional<Categoria> cat = categoriaRepository.findById(id);
        if(cat.isEmpty()){
            throw new ObjectNotFoundException("Categoria com o id: " + id +", não encontrado! \nTipo: " + Categoria.class.getName());
        }
        return cat.get();
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria create(Categoria cat){
        return categoriaRepository.save(cat);
    }

    public Categoria update(CategoriaDTO categoriaDTO, Integer id){
        Categoria cat = findById(id);
        categoriaDTO.setId(cat.getId());
        BeanUtils.copyProperties(categoriaDTO, cat);
        return categoriaRepository.save(cat);
    }

    public void delete(Integer id){
        Categoria cat = findById(id);
        try{
            categoriaRepository.deleteById(id);
        }catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new DataIntegrityViolationException("Categoria não pode ser deletada pois existe item vinculado.");
        }


    }

}
