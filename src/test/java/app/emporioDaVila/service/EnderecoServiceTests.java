package app.emporioDaVila.service;

import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
import app.emporioDaVila.entity.Endereco;
import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.repository.EnderecoRepository;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTests {
    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    private Usuario usuario;
    private Endereco endereco;
    private Endereco enderecoQuebrado;

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

        enderecoQuebrado = new Endereco();
        enderecoQuebrado.setId(1L);
        enderecoQuebrado.setBairro("Bairro");
        enderecoQuebrado.setCep(null);
        enderecoQuebrado.setComplemento("");
        enderecoQuebrado.setNumero(null);
        enderecoQuebrado.setRua("");
        enderecoQuebrado.setUsuario(null);
    }

    // saveEndereco
    @DisplayName("Save salvando com sucesso")
    @Test
    void saveEndereco_cenario01() {
        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        String retorno = enderecoService.saveEndereco(endereco);

        assertEquals("Endereço salvo com sucesso", retorno);
    }

    @DisplayName("Save dando erro corretamente")
    @Test
    void saveEndereco_cenario02() {
        when(enderecoRepository.save(enderecoQuebrado)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(GenericExceptions.InvalidData.class, () -> enderecoService.saveEndereco(enderecoQuebrado));
    }

    @DisplayName("Save dando erro genérico")
    @Test
    void saveEndereco_cenario03() {
        when(enderecoRepository.save(enderecoQuebrado)).thenThrow(RuntimeException.class);

        assertThrows(GenericExceptions.General.class, () -> enderecoService.saveEndereco(enderecoQuebrado));
    }

    // findAll
    @DisplayName("Testando sucesso do findAll")
    @Test
    void findAll_cenario01() {
        List<Endereco> lista = new ArrayList<>();
        lista.add(endereco);

        when(enderecoRepository.findAll()).thenReturn(lista);

        List<Endereco> resultado = enderecoService.findAll();

        assertEquals(1, resultado.size());
        assertEquals(endereco.getBairro(), resultado.get(0).getBairro());
    }

    @DisplayName("Testando findAll recebendo lista vazia")
    @Test
    void findAll_cenario02() {
        when(enderecoRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(GenericExceptions.General.class, () -> enderecoService.findAll());
    }

    // findById
    @DisplayName("FindById retornando sucesso")
    @Test
    void findById_cenario01() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));

        Endereco resultado = enderecoService.findById(1L);

        assertEquals(endereco.getBairro(), resultado.getBairro());
    }

    @DisplayName("FindById retornando erro correto")
    @Test
    void findById_cenario02() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GenericExceptions.NotFound.class, () -> enderecoService.findById(1L));
    }

    // update
    @DisplayName("Update atualizando parcialmente com sucesso")
    @Test
    void update_cenario01() {
        Endereco updateData = new Endereco();
        updateData.setBairro("Bairro Atualizado");

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        Endereco resultado = enderecoService.update(1L, updateData);

        assertEquals("Bairro Atualizado", resultado.getBairro());
    }

    @DisplayName("Update dando erro corretamente")
    @Test
    void update_cenario03() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GenericExceptions.NotFound.class, () -> enderecoService.update(1L, endereco));
    }

    @DisplayName("Update atualizando por completo")
    @Test
    void update_cenario04() {
        Endereco novosDadosEndereco = new Endereco();


        novosDadosEndereco.setId(1L);
        novosDadosEndereco.setBairro("Mariana");
        novosDadosEndereco.setCep(696969);
        novosDadosEndereco.setComplemento("1995");
        novosDadosEndereco.setNumero(5958585);
        novosDadosEndereco.setRua("Brasil 2");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@example.com");
        usuario.setTelefone("45900000000");
        usuario.setSenha("123456");
        novosDadosEndereco.setUsuario(usuario);


        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        Endereco resultado = enderecoService.update(1L, novosDadosEndereco);

        assertEquals("Mariana", resultado.getBairro());
        assertEquals(696969, resultado.getCep());
        assertEquals("1995", resultado.getComplemento());
        assertEquals(5958585, resultado.getNumero());
        assertEquals("Brasil 2", resultado.getRua());
    }

    // delete
    @DisplayName("Delete deletando corretamente")
    @Test
    void delete_cenario01() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        doNothing().when(enderecoRepository).delete(endereco);

        assertDoesNotThrow(() -> enderecoService.delete(1L));
        verify(enderecoRepository, times(1)).delete(endereco);
    }

    @DisplayName("Endereço não encontrado no Delete")
    @Test
    void delete_cenario02() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GenericExceptions.NotFound.class, () -> enderecoService.delete(1L));
    }

    @DisplayName("FindByUsuarioId encontrando corretamente")
    @Test
    void findByUsuarioId_cenario01() {
        when(enderecoRepository.findByUsuarioId(1L)).thenReturn(List.of(endereco));

        List<Endereco> resultado = enderecoService.findByUsuarioId(1L);
        assertEquals(endereco.getBairro(), resultado.get(0).getBairro());
    }
}
