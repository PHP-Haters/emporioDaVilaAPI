package app.emporioDaVila.service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProdutoPedidoServiceTests {

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

    @Test
    @DisplayName("Salva pedido do produto com sucesso")
    void save_cenario01() {
        Produto produto = new Produto();
        produto.setId(1);

        Pedido pedido = new Pedido();
        pedido.setId(1L);

        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setProduto(produto);
        produtoPedido.setPedido(pedido);

        when(produtoPedidoRepository.save(produtoPedido)).thenReturn(produtoPedido);

        String retorno = this.produtoPedidoService.save(produtoPedido);
        assertEquals("Pedido do produto salvo com sucesso", retorno);
    }

    @Test
    @DisplayName("Retorna lista de pedidos do produto com sucesso")
    void findAll_cenario01() {
        List<ProdutoPedido> lista = new ArrayList<>();
        lista.add(produtoPedido);

        when(produtoPedidoRepository.findAll()).thenReturn(lista);

        List<ProdutoPedido> resultado = produtoPedidoRepository.findAll();

        assertEquals(1, resultado.size());
        assertEquals(produtoPedido.getId(), resultado.get(0).getId());
    }

    @Test
    @DisplayName("Retorna pedido do produto com id específico com sucesso")
    void findById_cenario01() {
        when(produtoPedidoRepository.findById(1)).thenReturn(Optional.of(produtoPedido));

        ProdutoPedido resultado = produtoPedidoService.findById(1);

        assertEquals(produtoPedido.getId(), resultado.getId());
    }

    @Test
    @DisplayName("Atualiza pedido do produto com sucesso")
    void update_cenario01() {
        ProdutoPedido existente = new ProdutoPedido();
        existente.setId(1);

        ProdutoPedido update = new ProdutoPedido();
        Produto produtoNovo = new Produto();
        produtoNovo.setId(2);
        update.setProduto(produtoNovo);

        when(produtoPedidoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(produtoPedidoRepository.save(existente)).thenReturn(existente);

        ProdutoPedido resultado = produtoPedidoService.update(1, update);

        assertNotNull(resultado);
        verify(produtoPedidoRepository, times(1)).save(existente);
    }

    @Test
    @DisplayName("Deleta pedido do produto com sucesso")
    void delete_cenario01() {
        when(produtoPedidoRepository.findById(1)).thenReturn(Optional.of(produtoPedido));
        doNothing().when(produtoPedidoRepository).delete(produtoPedido);

        assertDoesNotThrow(() -> produtoPedidoService.delete(1));
        verify(produtoPedidoRepository, times(1)).delete(produtoPedido);
    }

}
