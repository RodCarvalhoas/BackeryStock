package com.RodCarvalhoas.BackeryStock.controllers;

import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.dtos.CategoriaDTO;
import com.RodCarvalhoas.BackeryStock.service.CategoriaService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoriaControllerTest {

    public static final Integer ID        = 1;
    public static final String NAME       = "FRIOS E CONGELADOS";
    public static final String DESCRICAO  = "5 c° e Graus Negativos";

    @Mock
    CategoriaService categoriaService;

    @InjectMocks
    CategoriaController categoriaController;

    private Categoria categoria;
    private Categoria categoriaAtualizado;
    private CategoriaDTO categoriaDTO;
    private Optional<Categoria> categoriaOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCategoria();
    }

    @Test
    void whenFindByIdThenReturnAnCategoriaInstance() {
        when(categoriaService.findById(Mockito.anyInt())).thenReturn(categoria);

        ResponseEntity<Categoria> cat = categoriaController.findById(ID);

        assertNotNull(cat);
        assertEquals(ResponseEntity.class, cat.getClass());
        assertEquals(HttpStatus.OK, cat.getStatusCode());
        assertEquals(ID, cat.getBody().getId());
        assertEquals(NAME, cat.getBody().getName());
        assertEquals(DESCRICAO, cat.getBody().getDescricao());
    }

    @Test
    void whenFindAllThenReturnAnListOfCategoria() {
        when(categoriaService.findAll()).thenReturn(List.of(categoria));

        ResponseEntity<List<CategoriaDTO>> categoriaDTOList = categoriaController.findAll();

        assertNotNull(categoriaDTOList);
        assertEquals(ResponseEntity.class, categoriaDTOList.getClass());
        assertEquals(HttpStatus.OK, categoriaDTOList.getStatusCode());
        assertEquals(1, categoriaDTOList.getBody().size());
        assertEquals(ID, categoriaDTOList.getBody().get(0).getId());
        assertEquals(NAME, categoriaDTOList.getBody().get(0).getName());
        assertEquals(DESCRICAO, categoriaDTOList.getBody().get(0).getDescricao());

    }

    @Test
    void whenCreateTheReturnSuccess() {
        when(categoriaService.create(Mockito.any())).thenReturn(categoria);

        ResponseEntity<Categoria> cat = categoriaController.create(categoriaDTO);
        assertNotNull(cat);
        assertNotNull(cat.getHeaders().get("Location"));
        assertEquals(ResponseEntity.class, cat.getClass());
        assertEquals(HttpStatus.CREATED, cat.getStatusCode());

    }

    @Test
    void whenUpdateTheReturnSuccess() {
        when(categoriaService.update(Mockito.any(), Mockito.anyInt())).thenReturn(categoriaAtualizado);

        categoriaDTO.setName("SALGADOS E FRITOS");
        categoriaDTO.setDescricao("SALGADOS E DERIVADOS");

        ResponseEntity<CategoriaDTO> cat = categoriaController.update(categoriaDTO, ID);

        assertNotNull(cat);
        assertEquals(ResponseEntity.class, cat.getClass());
        assertEquals(HttpStatus.OK, cat.getStatusCode());
        assertEquals(ID, cat.getBody().getId());
        assertEquals("SALGADOS E FRITOS", cat.getBody().getName());
        assertEquals("SALGADOS E DERIVADOS", cat.getBody().getDescricao());
    }

    @Test
    void whenDeleteWithSuccess() {
        doNothing().when(categoriaService).delete(Mockito.anyInt());

        ResponseEntity<Void> cat = categoriaController.delete(ID);
        assertEquals(ResponseEntity.class, cat.getClass());
        assertEquals(HttpStatus.NO_CONTENT, cat.getStatusCode());
        Mockito.verify(categoriaService, Mockito.times(1)).delete(Mockito.any());
    }

    private void startCategoria(){
        categoria = new Categoria(ID, NAME, DESCRICAO);
        categoriaAtualizado = new Categoria(ID, "SALGADOS E FRITOS", "SALGADOS E DERIVADOS");
        categoriaDTO = new CategoriaDTO(ID, NAME, DESCRICAO);
        categoriaOptional = Optional.of(new Categoria(ID, NAME, DESCRICAO));

    }
}