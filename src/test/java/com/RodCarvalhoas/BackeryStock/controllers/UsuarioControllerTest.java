package com.RodCarvalhoas.BackeryStock.controllers;

import com.RodCarvalhoas.BackeryStock.Enums.Role;
import com.RodCarvalhoas.BackeryStock.domain.Usuario;
import com.RodCarvalhoas.BackeryStock.dtos.UsuarioRequest;
import com.RodCarvalhoas.BackeryStock.service.UsuarioService;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class UsuarioControllerTest {

    public static final long ID = 1;
    public static final String NAME = "Rodrigo";
    public static final String EMAIL = "sutil_rodrigo@hotmail.com";
    public static final String CPF = "71479382400";
    public static final Role ROLE = Role.ADMIN;
    public static final String PASSWORD = "senha123";
    
    private Usuario usuario;
    private Usuario usuarioAtualizado;
    private UsuarioRequest usuarioAtualizadoRequest;

    @Mock
    UsuarioService usuarioService;
    
    @InjectMocks
    UsuarioController usuarioController;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsuario();
    }

    @Test
    void whenFindByIdThenReturnAnUsuarioInstance() {
        when(usuarioService.findById(any())).thenReturn(usuario);

        ResponseEntity<Usuario> user = usuarioController.findById(ID);
        assertNotNull(user.getBody());
        assertEquals(Usuario.class, user.getBody().getClass());
        assertEquals(ID, user.getBody().getId());
        assertEquals(NAME, user.getBody().getName());
        assertEquals(EMAIL, user.getBody().getEmail());
        assertEquals(CPF, user.getBody().getCpf());
        assertEquals(ROLE, user.getBody().getRole());
        assertEquals(PASSWORD, user.getBody().getPassword());

    }

    @Test
    void whenFindAllThenReturnAnListOfUsuario() {
        when(usuarioService.findAll()).thenReturn(List.of(usuario));

        ResponseEntity<List<Usuario>> usuariosList = usuarioController.findAll();

        assertNotNull(usuariosList);
        assertEquals(1, usuariosList.getBody().size());
        assertEquals(Usuario.class, usuariosList.getBody().get(0).getClass());
        assertEquals(ID, usuariosList.getBody().get(0).getId());
        assertEquals(NAME, usuariosList.getBody().get(0).getName());
        assertEquals(EMAIL, usuariosList.getBody().get(0).getEmail());
        assertEquals(CPF, usuariosList.getBody().get(0).getCpf());
        assertEquals(ROLE, usuariosList.getBody().get(0).getRole());
        assertEquals(PASSWORD, usuariosList.getBody().get(0).getPassword());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(usuarioService.create(Mockito.any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> user = usuarioController.create(usuario);

        assertNotNull(user);
        assertNotNull(user.getHeaders().get("Location"));
        assertEquals(ResponseEntity.class, user.getClass());
        assertEquals(HttpStatus.CREATED, user.getStatusCode());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(usuarioService.update(Mockito.anyLong(), Mockito.any())).thenReturn(usuarioAtualizado);

        ResponseEntity<Usuario> user = usuarioController.update(ID, usuarioAtualizadoRequest);

        assertNotNull(user);
        assertEquals(ResponseEntity.class, user.getClass());
        assertEquals(HttpStatus.OK, user.getStatusCode());
        assertEquals(ID, user.getBody().getId());
        assertEquals("Rafael", user.getBody().getName());
        assertEquals("rafa@hotmail.com", user.getBody().getEmail());
        assertEquals("21406241814", user.getBody().getCpf());
        assertEquals(Role.USER, user.getBody().getRole());
        assertEquals("senha321", user.getBody().getPassword());
    }

    @Test
    void whenDeleteWithSuccess() {
        doNothing().when(usuarioService).deleteById(Mockito.any());

        ResponseEntity<Void> user = usuarioController.delete(ID);

        assertEquals(ResponseEntity.class, user.getClass());
        assertEquals(HttpStatus.NO_CONTENT, user.getStatusCode());
        Mockito.verify(usuarioService, Mockito.times(1)).deleteById(Mockito.any());

    }

    private void startUsuario(){
        usuario = new Usuario(ID, NAME,EMAIL, CPF, ROLE, PASSWORD);
        usuarioAtualizadoRequest = new UsuarioRequest(ID, NAME,EMAIL, CPF, ROLE, PASSWORD);
        usuarioAtualizado = new Usuario(ID, "Rafael"
                , "rafa@hotmail.com"
                , "21406241814"
                , Role.USER
                , "senha321");
    }
}