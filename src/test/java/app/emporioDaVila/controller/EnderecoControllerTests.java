package app.emporioDaVila.controller;

import app.emporioDaVila.config.JwtAuthenticationFilter;
import app.emporioDaVila.entity.Endereco;
import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.service.EnderecoService;
import app.emporioDaVila.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EnderecoControllerTests {

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private EnderecoController enderecoController;

    private Endereco endereco;
    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@example.com");
        usuario.setTelefone("45900000000");
        usuario.setSenha("123456");

        endereco = new Endereco();
        endereco.setId(1L);
        endereco.setBairro("Bairro");
        endereco.setCep(10101023);
        endereco.setComplemento("Complemento");
        endereco.setNumero(123456789);
        endereco.setRua("Rua");
        endereco.setUsuario(usuario);
    }

    @DisplayName("Deve salvar o endereço com sucesso")
    @Test
    void saveEndereco_cenarioSucesso() {
        when(enderecoService.saveEndereco(endereco)).thenReturn("Endereço salvo com sucesso");

        ResponseEntity<String> response = enderecoController.saveEndereco(endereco);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Endereço salvo com sucesso", response.getBody());
        verify(enderecoService, times(1)).saveEndereco(endereco);
    }

    @DisplayName("Deve retornar lista de endereços com sucesso")
    @Test
    void findAll_cenarioSucesso() {
        List<Endereco> lista = Arrays.asList(endereco, new Endereco());
        when(enderecoService.findAll()).thenReturn(lista);

        ResponseEntity<List<Endereco>> response = enderecoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(enderecoService, times(1)).findAll();
    }

    @DisplayName("Deve retornar um único endereço com sucesso")
    @Test
    void findById_cenarioSucesso() {
        when(enderecoService.findById(1L)).thenReturn(endereco);

        ResponseEntity<Endereco> response = enderecoController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Bairro", response.getBody().getBairro());
        verify(enderecoService, times(1)).findById(1L);
    }

    @DisplayName("Deve atualizar o endereço ocm sucesso")
    @Test
    void update_cenarioSucesso() {
        when(enderecoService.update(endereco.getId(), endereco)).thenReturn(endereco);

        ResponseEntity<String> response = enderecoController.update(endereco.getId(), endereco);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se o update foi chamado
        verify(enderecoService, times(1)).update(eq(1L), any(Endereco.class));
    }
    @DisplayName("Deve deletear o endereço com sucesso")
    @Test
    void delete_cenarioSucesso() {
        doNothing().when(enderecoService).delete(1L);

        ResponseEntity<?> response = enderecoController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(enderecoService, times(1)).delete(1L);
    }
    @DisplayName("Deve retornar um único endereço com sucesso")
    @Test
    void findByUsuarioId_cenarioSucesso() {
        when(enderecoService.findByUsuarioId(1L)).thenReturn(List.of(endereco));

        ResponseEntity<List<Endereco>> response = enderecoController.findByUsuarioId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Bairro", response.getBody().get(0).getBairro());
        verify(enderecoService, times(1)).findByUsuarioId(1L);
    }

}