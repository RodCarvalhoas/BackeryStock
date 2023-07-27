package com.RodCarvalhoas.BackeryStock.service;

import com.RodCarvalhoas.BackeryStock.Enums.UnMedida;
import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.domain.Item;
import com.RodCarvalhoas.BackeryStock.exceptions.ObjectNotFoundException;
import com.RodCarvalhoas.BackeryStock.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    public static final Integer ID         = 1;
    public static final String NAME        = "Coxinha Congelada";
    public static final Integer QUANTIDADE = 20;
    public static final Double VALOR_UN = 2.5;
    public static final UnMedida UN_MEDIDA = UnMedida.KG;
    public static final String ITEM_NAO_ENCONTRADO        = "Item com o id: " + ID + " não pode ser encontrado.";



    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CategoriaService categoriaService;

    private Item item;
    private Item itemPatch;
    private Optional<Item> itemOptional;
    private List<Item> itemList = new ArrayList<>();
    private Categoria categoria = new Categoria(1, "Congelados", "Frios & Congelados");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startItem();
    }

    @Test
    void whenFindByIdThenReturnAnItemInstance() {
        when(itemRepository.findById(anyInt())).thenReturn(itemOptional);

        Item item = itemService.findById(ID);

        assertNotNull(item);
        assertEquals(ID, item.getId());
        assertEquals(NAME, item.getName());
        assertEquals(QUANTIDADE, item.getQuantidade());
        assertEquals(VALOR_UN, item.getValorUn());
        assertEquals(UN_MEDIDA, item.getUnMedida());
    }
    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(itemRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(ITEM_NAO_ENCONTRADO));
        try{
        Item item = itemService.findById(2);
        }catch (Exception ex){
            assertNotNull(ex);
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(ITEM_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindAllByCategoriaThenReturnAnListOfCategoria() {
        when(categoriaService.findById(Mockito.anyInt())).thenReturn(categoria);
        when(itemRepository.findAllByCategoria(Mockito.anyInt())).thenReturn(itemList);

        List<Item> items = itemService.findAllByCategoria(1);

        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(ID, items.get(0).getId());
        assertEquals(NAME, items.get(0).getName());
        assertEquals(QUANTIDADE, items.get(0).getQuantidade());
        assertEquals(VALOR_UN, items.get(0).getValorUn());
        assertEquals(UN_MEDIDA, items.get(0).getUnMedida());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(categoriaService.findById(Mockito.anyInt())).thenReturn(categoria);
        when(itemRepository.save(Mockito.any())).thenReturn(item);
        when(itemRepository.findById(Mockito.anyInt())).thenReturn(itemOptional);

        Item itemAtualizado = itemService.update(ID, item, categoria.getId());

        assertNotNull(itemAtualizado);
        assertEquals(ID, itemAtualizado.getId());
        assertEquals(NAME, itemAtualizado.getName());
        assertEquals(QUANTIDADE, itemAtualizado.getQuantidade());
        assertEquals(VALOR_UN, itemAtualizado.getValorUn());
        assertEquals(categoria, itemAtualizado.getCategoria());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(categoriaService.findById(Mockito.anyInt())).thenReturn(categoria);
        when(itemRepository.save(Mockito.any())).thenReturn(item);

        Item it = itemService.create(item, categoria.getId());

        assertNotNull(it);
        assertEquals(ID, it.getId());
        assertEquals(NAME, it.getName());
        assertEquals(QUANTIDADE, it.getQuantidade());
        assertEquals(VALOR_UN, it.getValorUn());
        assertEquals(categoria, it.getCategoria());
    }

    @Test
    void whenDeleteByIdTheReturnSuccess() {
        when(itemRepository.findById(Mockito.anyInt())).thenReturn(itemOptional);
        doNothing().when(itemRepository).deleteById(Mockito.anyInt());

        itemService.deleteById(ID);
        Mockito.verify(itemRepository, Mockito.times(1)).deleteById(Mockito.anyInt());
    }

    @Test
    void whenItemOutputThenReturnSuccess() {
        when(itemRepository.findById(Mockito.anyInt())).thenReturn(itemOptional);
        when(itemRepository.save(Mockito.any())).thenReturn(itemPatch);


        Item it = itemService.itemOutput(ID, 15);

        assertNotNull(it);
        assertEquals(ID, it.getId());
        assertEquals(NAME, it.getName());
        assertEquals(5, it.getQuantidade());
        assertEquals(VALOR_UN, it.getValorUn());
        assertEquals(categoria, it.getCategoria());

    }

    private void startItem(){
        item = new Item(ID, NAME, QUANTIDADE, VALOR_UN, UN_MEDIDA, categoria);
        itemOptional = Optional.of(new Item(ID, NAME, QUANTIDADE, VALOR_UN, UN_MEDIDA, categoria));
        itemPatch = new Item(ID, NAME, 5, VALOR_UN, UN_MEDIDA, categoria);
        itemList.add(item);
    }

}