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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
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
    private Item itemAtualizado;
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
    void whenUpdateThenReturnSuccess() {
        when(itemService.update(Mockito.anyInt(),Mockito.any(),Mockito.anyInt())).thenReturn(itemAtualizado);

        ResponseEntity<ItemDTO> it = itemController.update(ID, item, categoria.getId());

        assertNotNull(it);
        assertEquals(ResponseEntity.class, it.getClass());
        assertEquals(HttpStatus.OK, it.getStatusCode());
        assertEquals(ID, it.getBody().getId());
        assertEquals("ARROZ", it.getBody().getName());
        assertEquals(80, it.getBody().getQuantidade());
        assertEquals(4.25, it.getBody().getValorUn());
        assertEquals(UnMedida.KG, it.getBody().getUnMedida());
        assertEquals(categoria, it.getBody().getCategoria());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(itemService.create(Mockito.any(), Mockito.anyInt())).thenReturn(item);

        ResponseEntity<Item> it = itemController.create(item, categoria.getId());

        assertNotNull(it);
        assertNotNull(it.getHeaders().get("Location"));
        assertEquals(ResponseEntity.class, it.getClass());
        assertEquals(HttpStatus.CREATED, it.getStatusCode());
    }

    @Test
    void whenDeleteWithSuccess() {
        doNothing().when(itemService).deleteById(Mockito.anyInt());

        ResponseEntity<Void> it = itemController.delete(ID);

        assertEquals(ResponseEntity.class, it.getClass());
        assertEquals(HttpStatus.NO_CONTENT, it.getStatusCode());
        Mockito.verify(itemService, Mockito.times(1)).deleteById(Mockito.any());

    }

    @Test
    void whenItemOutputThenReturnSuccess() {
        when(itemService.itemOutput(Mockito.anyInt(), Mockito.anyInt())).thenReturn(item);

        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("quantidade", 15);

        ResponseEntity<ItemDTO> it = itemController.itemOutput(ID, requestBody);

        assertNotNull(it);
        assertEquals(ResponseEntity.class, it.getClass());
        assertEquals(HttpStatus.OK, it.getStatusCode());

        // Subtrair 15 da quantidade inicial (5) para verificar se a quantidade foi reduzida corretamente para 5 - 15 = -10
        assertEquals(-10, it.getBody().getQuantidade() - 15);
    }


    private void startItem(){
        item = new Item(ID, NAME, QUANTIDADE, VALOR_UN, UN_MEDIDA, categoria);
        itemAtualizado = new Item(ID, "ARROZ", 80, 4.25, UnMedida.KG, categoria);
        itemDTO = new ItemDTO(ID, NAME, QUANTIDADE, VALOR_UN, UN_MEDIDA, categoria);
    }
}