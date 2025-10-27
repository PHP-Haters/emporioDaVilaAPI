package app.emporioDaVila.service;

import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import app.emporioDaVila.service.ProdutoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTests {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setup() {
        produto = new Produto();
        produto.setId(1);
        produto.setNome("café");

    }

    // saveProduto
    @Test
    void saveProduto_cenario() {
        // Salva Produto com sucesso
        when(produtoRepository.save(produto)).thenReturn(produto);

        String retorno = produtoService.save(produto);

        assertEquals("Produto salvo com sucesso", retorno);
    }

    // finAll
    @Test
    void findAll_cenario() {
        // Sucesso: retorna lista de usuários
        List<Produto> lista = new ArrayList<>();
        lista.add(produto);

        when(produtoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(lista);

        List<Produto> resultado = produtoService.findAll();

        assertEquals(1, resultado.size());
        assertEquals(produto.getNome(), resultado.get(0).getNome());
    }

    // findById
    @Test
    void findById_cenario() {
        // Sucesso: retorna o usuário encontrado
        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));

        Produto resultado = produtoService.findById(1);

        assertEquals(produto.getNome(), resultado.getNome());
    }

    // update
    @Test
    void update_cenario() {
        // Sucesso: atualiza usuário parcialmente
        Produto updateData = new Produto();
        updateData.setNome("café");

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto resultado = produtoService.update(1, updateData);

        assertEquals(updateData.getNome(), resultado.getNome());
    }

    // delete
    @Test
    void delete_cenario() {
        // Sucesso: usuário deletado sem exceção
        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).delete(produto);

        assertDoesNotThrow(() -> produtoService.delete(1));
        verify(produtoRepository, times(1)).delete(produto);
    }

}