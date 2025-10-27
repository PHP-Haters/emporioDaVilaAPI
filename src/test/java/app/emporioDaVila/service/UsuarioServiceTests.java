package app.emporioDaVila.service;

import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Usuario usuarioQuebrado;

    @BeforeEach
    void setup() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@example.com");
        usuario.setTelefone("45900000000");
        usuario.setSenha("123456");

        usuarioQuebrado = new Usuario();
        usuarioQuebrado.setId(2L);
        usuarioQuebrado.setNome("Claudio");
        usuarioQuebrado.setEmail(null);
        usuarioQuebrado.setTelefone(null);
        usuarioQuebrado.setSenha(null);
    }

    // saveUsuario
    @Test
    void saveUsuario_cenario01() {
        // Salva usuario com sucesso
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        String retorno = usuarioService.saveUsuario(usuario);

        assertEquals("Usuário salvo com sucesso.", retorno);
    }

    @Test
    void saveUsuario_cenario02() {
        // Erro por usuário com campos inválidos ou faltando
        when(usuarioRepository.save(usuarioQuebrado)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(GenericExceptions.InvalidData.class, () -> usuarioService.saveUsuario(usuarioQuebrado));
    }

    @Test
    void saveUsuario_cenario03() {
        // Erro genérico
        when(usuarioRepository.save(usuarioQuebrado)).thenThrow(RuntimeException.class);

        assertThrows(GenericExceptions.General.class, () -> usuarioService.saveUsuario(usuarioQuebrado));
    }

    // finAll
    @Test
    void findAll_cenario01() {
        // Sucesso: retorna lista de usuários
        List<Usuario> lista = new ArrayList<>();
        lista.add(usuario);

        when(usuarioRepository.findAll()).thenReturn(lista);

        List<Usuario> resultado = usuarioService.findAll();

        assertEquals(1, resultado.size());
        assertEquals(usuario.getNome(), resultado.get(0).getNome());
    }

    @Test
    void findAll_cenario02() {
        // Erro: lista vazia → lança General
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(GenericExceptions.General.class, () -> usuarioService.findAll());
    }

    // findById
    @Test
    void findById_cenario01() {
        // Sucesso: retorna o usuário encontrado
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findById(1L);

        assertEquals(usuario.getNome(), resultado.getNome());
    }

    @Test
    void findById_cenario02() {
        // Erro: usuário não encontrado → lança NotFound
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GenericExceptions.NotFound.class, () -> usuarioService.findById(1L));
    }

    // update
    @Test
    void update_cenario01() {
        // Sucesso: atualiza usuário parcialmente
        Usuario updateData = new Usuario();
        updateData.setNome("João Atualizado");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.update(1L, updateData);

        assertEquals("João Atualizado", resultado.getNome());
    }

    @Test
    @DisplayName("suário não encontrado → lança NotFound")
    void update_cenario02() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GenericExceptions.NotFound.class, () -> usuarioService.update(1L, usuario));
    }

    @Test
    void update_cenario03() {
        // Sucesso: atualiza usuário por completo
        Usuario novosDadosUsuario = new Usuario();
        novosDadosUsuario.setNome("Novo nome");
        novosDadosUsuario.setSenha("Nova senha");
        novosDadosUsuario.setTelefone("45999999999");
        novosDadosUsuario.setEmail("novoEmail@email.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.update(1L, novosDadosUsuario);

        assertEquals("Novo nome", resultado.getNome());
        assertEquals("Nova senha", resultado.getSenha());
        assertEquals("45999999999", resultado.getTelefone());
        assertEquals("novoEmail@email.com", resultado.getEmail());
    }

    // delete
    @Test
    void delete_cenario01() {
        // Sucesso: usuário deletado sem exceção
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        assertDoesNotThrow(() -> usuarioService.delete(1L));
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void delete_cenario02() {
        // Erro: usuário não encontrado → lança NotFound
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GenericExceptions.NotFound.class, () -> usuarioService.delete(1L));
    }

    // login
    @Test
    void login_cenario01() {
        // Sucesso: login correto → retorna usuário com dados nulos
        Usuario loginUsuario = new Usuario();
        loginUsuario.setEmail("joao@example.com");
        loginUsuario.setSenha("123456");

        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.login(loginUsuario);

        assertNull(resultado.getEmail());
        assertNull(resultado.getNome());
        assertNull(resultado.getSenha());
        assertNull(resultado.getTelefone());
    }

    @Test
    void login_cenario02() {
        // Erro: usuário não encontrado → lança NotFound
        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.empty());

        Usuario loginUsuario = new Usuario();
        loginUsuario.setEmail("joao@example.com");
        loginUsuario.setSenha("123456");

        assertThrows(GenericExceptions.NotFound.class, () -> usuarioService.login(loginUsuario));
    }

    @Test
    void login_cenario03() {
        // Erro: senha incorreta → lança Unauthorized
        Usuario loginUsuario = new Usuario();
        loginUsuario.setEmail("joao@example.com");
        loginUsuario.setSenha("senhaErrada");

        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(usuario));

        assertThrows(GenericExceptions.Unauthorized.class, () -> usuarioService.login(loginUsuario));
    }
}
