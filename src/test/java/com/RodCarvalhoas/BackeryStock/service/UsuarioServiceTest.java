package com.RodCarvalhoas.BackeryStock.service;

import com.RodCarvalhoas.BackeryStock.Enums.Role;
import com.RodCarvalhoas.BackeryStock.domain.Usuario;
import com.RodCarvalhoas.BackeryStock.dtos.UsuarioRequest;
import com.RodCarvalhoas.BackeryStock.exceptions.DataIntegrityViolationException;
import com.RodCarvalhoas.BackeryStock.exceptions.ObjectNotFoundException;
import com.RodCarvalhoas.BackeryStock.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    public static final long ID = 1;
    public static final String NAME = "Rodrigo";
    public static final String EMAIL = "sutil_rodrigo@hotmail.com";
    public static final String CPF = "71479382400";
    public static final Role ROLE = Role.ADMIN;
    public static final String PASSWORD = "senha123";
    public static final String USUARIO_NAO_ENCONTRADO = "Usuario com o Id: " + ID + " não encontrado";


    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private Optional<Usuario> usuarioOptional;
    private UsuarioRequest usuarioAtualizadoRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsuario();
    }

    @Test
    void whenFindByIdThenReturnAnUsuarioInstance() {
        when(usuarioRepository.findById(any())).thenReturn(usuarioOptional);

        Usuario user = usuarioService.findById(ID);

        assertNotNull(user);
        assertEquals(ID, user.getId());
        assertEquals(NAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(CPF, user.getCpf());
        assertEquals(ROLE, user.getRole());
        assertEquals(PASSWORD, user.getPassword());
    }
    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(usuarioRepository.findById(any())).thenThrow(new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO));
        try{
            Usuario user = usuarioService.findById(ID);
        }catch (Exception ex){
            assertNotNull(ex);
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(USUARIO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenfindAllThenReturnAnListOfUsuario() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> listUser = usuarioService.findAll();

        assertNotNull(listUser);
        assertEquals(1, listUser.size());
        assertEquals(Usuario.class, listUser.get(0).getClass());
        assertEquals(ID, listUser.get(0).getId());
        assertEquals(NAME, listUser.get(0).getName());
        assertEquals(EMAIL, listUser.get(0).getEmail());
        assertEquals(CPF, listUser.get(0).getCpf());
        assertEquals(ROLE, listUser.get(0).getRole());
        assertEquals(PASSWORD, listUser.get(0).getPassword());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);

        Usuario user = usuarioService.create(usuario);

        assertNotNull(user);
        assertEquals(ID, user.getId());
        assertEquals(NAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(CPF, user.getCpf());
        assertEquals(ROLE, user.getRole());
        assertEquals(PASSWORD, user.getPassword());
    }
    @Test
    void whenCreateWithExistingNameThenThrowDataIntegrityViolationException() {
        when(usuarioRepository.findByName(Mockito.anyString())).thenReturn(usuario);
        assertThrows(DataIntegrityViolationException.class, () -> usuarioService.create(usuario));
    }
    @Test
    void whenCreateWithExistingEmailThenThrowDataIntegrityViolationException() {
        when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioOptional);
        assertThrows(DataIntegrityViolationException.class, () -> usuarioService.create(usuario));
    }
    @Test
    void whenCreateWithExistingCPFThenThrowDataIntegrityViolationException() {
        when(usuarioRepository.findBycpf(Mockito.anyString())).thenReturn(usuarioOptional);
        assertThrows(DataIntegrityViolationException.class, () -> usuarioService.create(usuario));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(usuarioRepository.findById(Mockito.any())).thenReturn(usuarioOptional);
        when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);

        Usuario usuarioAtualizado = usuario;
        usuarioAtualizado.setName("Rafael");
        usuarioAtualizado.setCpf("21406241814");

        Usuario objUpdate = usuarioService.update(ID, usuarioAtualizadoRequest);

        assertNotNull(objUpdate);
        assertEquals(ID, objUpdate.getId());
        assertEquals("Rafael", objUpdate.getName());
        assertEquals("21406241814", objUpdate.getCpf());
        assertEquals(PASSWORD, objUpdate.getPassword());
        assertEquals(EMAIL, objUpdate.getEmail());
        assertEquals(ROLE, objUpdate.getRole());
    }

    @Test
    void deleteByIdWithSuccess() {
        when(usuarioRepository.findById(Mockito.any())).thenReturn(usuarioOptional);
        doNothing().when(usuarioRepository).deleteById(Mockito.any());

        usuarioService.deleteById(ID);

        Mockito.verify(usuarioRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    void whenLoadUserByUsernameThenReturnAnUserDetailsInstance() {
        when(usuarioRepository.findByName(Mockito.any())).thenReturn(usuario);

        UserDetails userDetails = usuarioService.loadUserByUsername(NAME);

        assertNotNull(userDetails);
        assertEquals(usuario, userDetails);
    }

    private void startUsuario(){
        usuario = new Usuario(ID, NAME,EMAIL, CPF, ROLE, PASSWORD);
        usuarioOptional = Optional.of(new Usuario(ID, NAME,EMAIL, CPF, ROLE, PASSWORD));
        usuarioAtualizadoRequest = new UsuarioRequest(ID, NAME,EMAIL, CPF, ROLE, PASSWORD);
    }
}