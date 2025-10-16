package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTests {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@example.com");
        usuario.setSenha("123456");
    }

    // ---------------------------
    // TESTE: saveUsuario
    // ---------------------------
    @Test
    void deveSalvarUsuarioComSucesso() {
        when(usuarioService.saveUsuario(usuario)).thenReturn("Usuário salvo com sucesso.");

        ResponseEntity<String> response = usuarioController.saveUsuario(usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário salvo com sucesso.", response.getBody());
        verify(usuarioService, times(1)).saveUsuario(usuario);
    }

    // ---------------------------
    // TESTE: findAll
    // ---------------------------
    @Test
    void deveRetornarListaDeUsuarios() {
        List<Usuario> lista = Arrays.asList(usuario, new Usuario());
        when(usuarioService.findAll()).thenReturn(lista);

        ResponseEntity<List<Usuario>> response = usuarioController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(usuarioService, times(1)).findAll();
    }

    // ---------------------------
    // TESTE: findById
    // ---------------------------
    @Test
    void deveRetornarUsuarioPorId() {
        when(usuarioService.findById(1L)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João", response.getBody().getNome());
        verify(usuarioService, times(1)).findById(1L);
    }

    // ---------------------------
    // TESTE: update
    // ---------------------------
    @Test
    void deveAtualizarUsuarioComSucesso() {
        // Não precisamos do doNothing() se o método é void
        // Mockito já garante que o mock não fará nada

        ResponseEntity<String> response = usuarioController.update(1L, usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário atualizado com sucesso.", response.getBody());

        // Verifica se o update foi chamado
        verify(usuarioService, times(1)).update(eq(1L), any(Usuario.class));
    }

    // ---------------------------
    // TESTE: delete
    // ---------------------------
    @Test
    void deveDeletarUsuarioComSucesso() {
        doNothing().when(usuarioService).delete(1L);

        ResponseEntity<?> response = usuarioController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).delete(1L);
    }

    // ---------------------------
    // TESTE: login
    // ---------------------------
    @Test
    void deveFazerLoginComSucesso() {
        when(usuarioService.login(usuario)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.findById(usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João", response.getBody().getNome());
        verify(usuarioService, times(1)).login(usuario);
    }
}
