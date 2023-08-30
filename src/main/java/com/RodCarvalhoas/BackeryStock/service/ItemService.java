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
        return item.orElseThrow(() -> new ObjectNotFoundException("Item com o id: " + id + " não pode ser encontrado."));
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



    public Item create(Item item, Integer id_cat) {
        Categoria categoria = categoriaService.findById(id_cat);
        item.setCategoria(categoria);
        item.setTotal(calcularValorTotal(item));
        return itemRepository.save(item);
    }

    private Double calcularValorTotal(Item item){
        return item.getQuantidade() * item.getValorUn();
    }

    public void deleteById(Integer id) {
        Item item = findById(id);
        itemRepository.deleteById(item.getId());
    }

    public Item itemOutput(Integer id, Integer quantidade) {
        Item item = findById(id);
        retiraQuantidade(item, quantidade);
        return itemRepository.save(item);
    }

    public void retiraQuantidade(Item item, Integer quantidade){
        Integer quantidadeAtual = item.getQuantidade();
        Integer novaQuantidade = quantidadeAtual - quantidade;
        item.setQuantidade(novaQuantidade);
    }

    public Item entryItem(Integer id, Integer quantidade){
        Item item = findById(id);
        adicionaQuantidade(item, quantidade);
        return itemRepository.save(item);
    }

    public void adicionaQuantidade(Item item, Integer quantidade){
        Integer quantidadeAtual = item.getQuantidade();
        Integer quantidadeAdicionar = quantidadeAtual + quantidade;
        item.setQuantidade(quantidadeAdicionar);
    }
}
