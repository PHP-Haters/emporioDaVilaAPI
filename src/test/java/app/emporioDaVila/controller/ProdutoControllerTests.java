package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Enum.Categoria;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoControllerTests {

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    private Produto produto;

    @BeforeEach
    void setup() {
        produto = new Produto();
        produto.setId(1);
        produto.setNome("Café Especial");
        produto.setValor(10.5f);
        produto.setStock(true);
        produto.setImagem("cafe.png");
        produto.setCategoria(Categoria.GRAOS);
    }

    @Test
    void save_cenarioSucesso() {
        when(produtoService.save(produto)).thenReturn("Produto salvo com sucesso.");

        ResponseEntity<String> response = produtoController.save(produto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto salvo com sucesso.", response.getBody());
        verify(produtoService, times(1)).save(produto);
    }

    @Test
    void findAll_cenarioSucesso() {
        List<Produto> lista = Arrays.asList(produto, new Produto());
        when(produtoService.findAll()).thenReturn(lista);

        ResponseEntity<List<Produto>> response = produtoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(produtoService, times(1)).findAll();
    }

    @Test
    void findById_cenarioSucesso() {
        when(produtoService.findById(1)).thenReturn(produto);

        ResponseEntity<Produto> response = produtoController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Café Especial", response.getBody().getNome());
        verify(produtoService, times(1)).findById(1);
    }

    @Test
    void getCategorias_cenarioSucesso() {
        List<Categoria> categorias = Arrays.asList(Categoria.GRAOS, Categoria.GRAOS);
        when(produtoService.listCategorias()).thenReturn(categorias);

        ResponseEntity<List<Categoria>> response = produtoController.getCategorias();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(produtoService, times(1)).listCategorias();
    }

    @Test
    void findByCategoria_cenarioSucesso() {
        List<Produto> lista = Collections.singletonList(produto);
        when(produtoService.findByCategoria(Categoria.GRAOS)).thenReturn(lista);

        ResponseEntity<List<Produto>> response = produtoController.findByCategoria(Categoria.GRAOS);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(produtoService, times(1)).findByCategoria(Categoria.GRAOS);
    }

    @Test
    void update_cenarioSucesso() {
        when(produtoService.update(produto.getId(), produto)).thenReturn(produto);

        ResponseEntity<String> response = produtoController.update(1, produto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto atualizado com sucesso.", response.getBody());
        verify(produtoService, times(1)).update(eq(1), any(Produto.class));
    }

    @Test
    void delete_cenarioSucesso() {
        doNothing().when(produtoService).delete(1);

        ResponseEntity<?> response = produtoController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(produtoService, times(1)).delete(1);
    }
}