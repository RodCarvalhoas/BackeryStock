package com.RodCarvalhoas.BackeryStock.service;

import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.dtos.CategoriaDTO;
import com.RodCarvalhoas.BackeryStock.exceptions.DataIntegrityViolationException;
import com.RodCarvalhoas.BackeryStock.exceptions.ObjectNotFoundException;
import com.RodCarvalhoas.BackeryStock.repositories.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    public static final Integer ID        = 1;
    public static final String NAME       = "FRIOS E CONGELADOS";
    public static final String DESCRICAO  = "5 c° e Graus Negativos";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final String CATEGORIA_NAO_ENCONTRADA = "Categoria com o id: " + ID + ", não encontrado! \nTipo: " + Categoria.class.getName();

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;
    private CategoriaDTO categoriaDTO;
    private Optional<Categoria> categoriaOptional;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCategoria();
    }

    @Test
    void whenFindByIdThenReturnAnCategoriaInstance() {
        when(categoriaRepository.findById(anyInt())).thenReturn(categoriaOptional);

        Categoria cat = categoriaService.findById(ID);

        assertNotNull(cat);
        assertEquals(Categoria.class, cat.getClass());
        assertEquals(ID, cat.getId());
        assertEquals(NAME, cat.getName());
        assertEquals(DESCRICAO, cat.getDescricao());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(categoriaRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(CATEGORIA_NAO_ENCONTRADA));
        try{
            categoriaService.findById(ID);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(CATEGORIA_NAO_ENCONTRADA, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfCategoria() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

        List<Categoria> categoriaList = categoriaService.findAll();

        assertNotNull(categoriaList);

        assertEquals(1, categoriaList.size());
        assertEquals(Categoria.class, categoriaList.get(0).getClass());
        assertEquals(ID, categoriaList.get(0).getId());
        assertEquals(NAME, categoriaList.get(0).getName());
        assertEquals(DESCRICAO, categoriaList.get(0).getDescricao());
    }

    @Test
    void whenCreateCategoriaThenReturnSuccess() {
        when(categoriaRepository.save(Mockito.any())).thenReturn(categoria);

        Categoria categoriaResponse = categoriaService.create(categoria);

        assertNotNull(categoriaResponse);

        assertEquals(Categoria.class, categoriaResponse.getClass());
        assertEquals(ID,categoriaResponse.getId());
        assertEquals(NAME, categoriaResponse.getName());
        assertEquals(DESCRICAO, categoriaResponse.getDescricao());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(categoriaRepository.save(Mockito.any())).thenReturn(categoria);
        when(categoriaRepository.findById(Mockito.anyInt())).thenReturn(categoriaOptional);

        Categoria categoriaResponse = categoriaService.update(categoriaDTO, ID);

        assertNotNull(categoriaResponse);
        assertEquals(ID, categoriaResponse.getId());
        assertEquals(NAME, categoriaResponse.getName());
        assertEquals(DESCRICAO, categoriaResponse.getDescricao());
    }

    @Test
    void deleteCategoriaWithSucess() {
        when(categoriaRepository.findById(Mockito.anyInt())).thenReturn(categoriaOptional);
        doNothing().when(categoriaRepository).deleteById(Mockito.anyInt());

        categoriaService.delete(ID);
        Mockito.verify(categoriaRepository, Mockito.times(1)).deleteById(Mockito.anyInt());
    }

    @Test
    void deleteCategoriaWithDataIntegrityViolationException() {
        when(categoriaRepository.findById(Mockito.anyInt())).thenReturn(categoriaOptional);
        Mockito.doThrow(new org.springframework.dao.DataIntegrityViolationException("Categoria não pode ser deletada pois existe item vinculado.")).when(categoriaRepository).deleteById(Mockito.anyInt());

        try{
            categoriaService.delete(ID);
        } catch (Exception ex){
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Categoria não pode ser deletada pois existe item vinculado.", ex.getMessage());
        }

    }

    private void startCategoria(){
        categoria = new Categoria(ID, NAME, DESCRICAO);
        categoriaDTO = new CategoriaDTO(ID, NAME, DESCRICAO);
        categoriaOptional = Optional.of(new Categoria(ID, NAME, DESCRICAO));
    }

}