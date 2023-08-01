package com.RodCarvalhoas.BackeryStock.controllers;

import com.RodCarvalhoas.BackeryStock.Enums.UnMedida;
import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.domain.Item;
import com.RodCarvalhoas.BackeryStock.dtos.ItemDTO;
import com.RodCarvalhoas.BackeryStock.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemControllerTest {

    public static final Integer ID         = 1;
    public static final String NAME        = "Coxinha Congelada";
    public static final Integer QUANTIDADE = 20;
    public static final Double VALOR_UN = 2.5;
    public static final UnMedida UN_MEDIDA = UnMedida.KG;
    private Categoria categoria = new Categoria(1, "Congelados", "Frios & Congelados");

    private Item item;
    private ItemDTO itemDTO;

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startItem();
    }

    @Test
    void whenFindByIdThenReturnAnItemInstance() {
        when(itemService.findById(Mockito.anyInt())).thenReturn(item);

        ResponseEntity<ItemDTO> it = itemController.findById(ID);

        assertNotNull(it);
        assertEquals(ResponseEntity.class, it.getClass());
        assertEquals(HttpStatus.OK, it.getStatusCode());
        assertEquals(ID, it.getBody().getId());
        assertEquals(NAME, it.getBody().getName());
        assertEquals(QUANTIDADE, it.getBody().getQuantidade());
        assertEquals(VALOR_UN, it.getBody().getValorUn());
        assertEquals(categoria, it.getBody().getCategoria());
    }

    @Test
    void whenFindAllThenReturnAnListOfItem() {
        when(itemService.findAllByCategoria(Mockito.anyInt())).thenReturn(List.of(item));

        ResponseEntity<List<ItemDTO>> itemDTOListByCategoria = itemController.findAll(ID);

        assertNotNull(itemDTOListByCategoria);
        assertEquals(ResponseEntity.class, itemDTOListByCategoria.getClass());
        assertEquals(HttpStatus.OK, itemDTOListByCategoria.getStatusCode());
        assertEquals(1, itemDTOListByCategoria.getBody().size());
        assertEquals(ID, itemDTOListByCategoria.getBody().get(0).getId());
        assertEquals(NAME, itemDTOListByCategoria.getBody().get(0).getName());
        assertEquals(QUANTIDADE, itemDTOListByCategoria.getBody().get(0).getQuantidade());
        assertEquals(VALOR_UN, itemDTOListByCategoria.getBody().get(0).getValorUn());
        assertEquals(UN_MEDIDA, itemDTOListByCategoria.getBody().get(0).getUnMedida());
        assertEquals(categoria, itemDTOListByCategoria.getBody().get(0).getCategoria());
    }

    @Test
    void update() {
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void itemOutput() {
    }

    private void startItem(){
        item = new Item(ID, NAME, QUANTIDADE, VALOR_UN, UN_MEDIDA, categoria);
        itemDTO = new ItemDTO(ID, NAME, QUANTIDADE, VALOR_UN, UN_MEDIDA, categoria);
    }
}