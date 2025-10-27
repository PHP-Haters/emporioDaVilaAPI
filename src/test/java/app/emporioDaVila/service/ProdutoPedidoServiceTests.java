package app.emporioDaVila.service;

import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
import app.emporioDaVila.entity.Enum.Categoria;
import app.emporioDaVila.entity.Pedido;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.entity.ProdutoPedido;
import app.emporioDaVila.repository.ProdutoPedidoRepository;
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
class ProdutoPedidoServiceTests {

    @Mock
    ProdutoPedidoRepository produtoPedidoRepository;

    @InjectMocks
    ProdutoPedidoService produtoPedidoService;

    ProdutoPedido produtoPedido;
    ProdutoPedido produtoPedidoQuebrado;

    @BeforeEach
    void setup() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setNome("Chá Verde");
        produto.setValor(12.5f);
        produto.setStock(true);
        produto.setImagem("imagem-cha-verde.jpg");
        produto.setCategoria(Categoria.SUPLEMENTOS);
        produto.setProdutosPedidos(new ArrayList<>());

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setIdUsuario(100L);
        pedido.setEstado(true);
        pedido.setProdutoPedidos(new ArrayList<>());
        pedido.setPagamentoPedidos(new ArrayList<>());

        produtoPedido = new ProdutoPedido();
        produtoPedido.setId(1);
        produtoPedido.setPedido(pedido);
        produtoPedido.setProduto(produto);

        produtoPedidoQuebrado = new ProdutoPedido();
        produtoPedidoQuebrado.setId(2);
        produtoPedidoQuebrado.setPedido(null);
        produtoPedidoQuebrado.setProduto(null);
    }

    // -------------------------------
    // SAVE
    // -------------------------------

    @Test
    @DisplayName("Salva pedido do produto com sucesso")
    void save_cenario01() {
        when(produtoPedidoRepository.save(produtoPedido)).thenReturn(produtoPedido);

        String retorno = produtoPedidoService.save(produtoPedido);

        assertEquals("Pedido do produto salvo com sucesso", retorno);
        verify(produtoPedidoRepository, times(1)).save(produtoPedido);
    }

    @Test
    @DisplayName("Lança exceção InvalidData ao salvar com dados inválidos")
    void save_cenario02() {
        when(produtoPedidoRepository.save(produtoPedidoQuebrado))
                .thenThrow(new DataIntegrityViolationException("dados inválidos"));

        GenericExceptions.InvalidData exception = assertThrows(
                GenericExceptions.InvalidData.class,
                () -> produtoPedidoService.save(produtoPedidoQuebrado)
        );

        assertTrue(exception.getMessage().contains("Dados inválidos"));
        verify(produtoPedidoRepository, times(1)).save(produtoPedidoQuebrado);
    }

    @Test
    @DisplayName("Lança exceção General ao ocorrer erro inesperado no save")
    void save_cenario03() {
        when(produtoPedidoRepository.save(produtoPedido))
                .thenThrow(new RuntimeException("falha desconhecida"));

        GenericExceptions.General exception = assertThrows(
                GenericExceptions.General.class,
                () -> produtoPedidoService.save(produtoPedido)
        );

        assertTrue(exception.getMessage().contains("Erro inesperado"));
        verify(produtoPedidoRepository, times(1)).save(produtoPedido);
    }

    // -------------------------------
    // FIND ALL
    // -------------------------------

    @Test
    @DisplayName("Retorna lista de pedidos do produto com sucesso")
    void findAll_cenario01() {
        List<ProdutoPedido> lista = new ArrayList<>();
        lista.add(produtoPedido);

        when(produtoPedidoRepository.findAll()).thenReturn(lista);

        List<ProdutoPedido> resultado = produtoPedidoService.findAll();

        assertEquals(1, resultado.size());
        assertEquals(produtoPedido.getId(), resultado.get(0).getId());
    }

    @Test
    @DisplayName("Lança exceção General ao não existir pedidos de produtos")
    void findAll_cenario02() {
        when(produtoPedidoRepository.findAll()).thenReturn(new ArrayList<>());

        GenericExceptions.General exception = assertThrows(
                GenericExceptions.General.class,
                () -> produtoPedidoService.findAll()
        );

        assertTrue(exception.getMessage().contains("Não existem produtos"));
        verify(produtoPedidoRepository, times(1)).findAll();
    }

    // -------------------------------
    // FIND BY ID
    // -------------------------------

    @Test
    @DisplayName("Retorna pedido do produto por ID com sucesso")
    void findById_cenario01() {
        when(produtoPedidoRepository.findById(1)).thenReturn(Optional.of(produtoPedido));

        ProdutoPedido resultado = produtoPedidoService.findById(1);

        assertEquals(produtoPedido.getId(), resultado.getId());
        verify(produtoPedidoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Lança exceção NotFound ao não encontrar pedido do produto por ID")
    void findById_cenario02() {
        when(produtoPedidoRepository.findById(99)).thenReturn(Optional.empty());

        GenericExceptions.NotFound exception = assertThrows(
                GenericExceptions.NotFound.class,
                () -> produtoPedidoService.findById(99)
        );

        assertTrue(exception.getMessage().contains("não encontrado"));
        verify(produtoPedidoRepository, times(1)).findById(99);
    }

    // -------------------------------
    // UPDATE
    // -------------------------------

    @Test
    @DisplayName("Atualiza produto pedido por completo")
    void update_cenario01() {
        ProdutoPedido existente = new ProdutoPedido();
        existente.setId(1);

        ProdutoPedido novo = new ProdutoPedido();
        Produto produtoNovo = new Produto();
        produtoNovo.setId(2);
        Pedido pedidoNovo = new Pedido();
        pedidoNovo.setId(2L);
        novo.setProduto(produtoNovo);
        novo.setPedido(pedidoNovo);

        when(produtoPedidoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(produtoPedidoRepository.save(existente)).thenReturn(existente);

        ProdutoPedido resultado = produtoPedidoService.update(1, novo);

        assertNotNull(resultado);
        verify(produtoPedidoRepository, times(1)).save(existente);
    }

    @Test
    @DisplayName("Lança exceção NotFound ao tentar atualizar pedido inexistente")
    void update_cenario02() {
        when(produtoPedidoRepository.findById(10)).thenReturn(Optional.empty());

        GenericExceptions.NotFound exception = assertThrows(
                GenericExceptions.NotFound.class,
                () -> produtoPedidoService.update(10, produtoPedido)
        );

        assertTrue(exception.getMessage().contains("não encontrado"));
        verify(produtoPedidoRepository, never()).save(any());
    }

    // -------------------------------
    // DELETE
    // -------------------------------

    @Test
    @DisplayName("Deleta pedido do produto com sucesso")
    void delete_cenario01() {
        when(produtoPedidoRepository.findById(1)).thenReturn(Optional.of(produtoPedido));
        doNothing().when(produtoPedidoRepository).delete(produtoPedido);

        assertDoesNotThrow(() -> produtoPedidoService.delete(1));
        verify(produtoPedidoRepository, times(1)).delete(produtoPedido);
    }

    @Test
    @DisplayName("Lança exceção NotFound ao tentar deletar pedido inexistente")
    void delete_cenario02() {
        when(produtoPedidoRepository.findById(100)).thenReturn(Optional.empty());

        GenericExceptions.NotFound exception = assertThrows(
                GenericExceptions.NotFound.class,
                () -> produtoPedidoService.delete(100)
        );

        assertTrue(exception.getMessage().contains("não encontrado"));
        verify(produtoPedidoRepository, never()).delete(any());
    }
}
