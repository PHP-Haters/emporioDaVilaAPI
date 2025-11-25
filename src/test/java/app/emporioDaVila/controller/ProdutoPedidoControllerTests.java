package app.emporioDaVila.controller;

import app.emporioDaVila.config.JwtAuthenticationFilter;
import app.emporioDaVila.config.JwtServiceGenerator;
import app.emporioDaVila.entity.Pedido;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.entity.ProdutoPedido;
import app.emporioDaVila.service.ProdutoPedidoService;
import org.junit.jupiter.api.Assertions;
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
class ProdutoPedidoControllerTests {

    @Mock
    private ProdutoPedidoService produtoPedidoService;

    @InjectMocks
    private ProdutoPedidoController produtoPedidoController;

    ProdutoPedido produtoPedido;

    @BeforeEach
    void setup() {
        Pedido pedido = new Pedido();
        pedido.setId(1L); // supondo que Pedido tem um campo id

        Produto produto = new Produto();
        produto.setId(10); // supondo que Produto também tem um campo id

        produtoPedido = new ProdutoPedido();
        produtoPedido.setId(1);
        produtoPedido.setPedido(pedido);
        produtoPedido.setProduto(produto);
    }

    @Test
    @DisplayName("Salva pedido do produto com sucesso")
    void save_cenario01() {
        when(produtoPedidoService.save(produtoPedido)).thenReturn("Pedido do produto salvo com sucesso");

        ResponseEntity<String> response = produtoPedidoController.save(produtoPedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pedido do produto salvo com sucesso", response.getBody());
        verify(produtoPedidoService, times(1)).save(produtoPedido);
    }

    @Test
    @DisplayName("Retorna lista de pedidos do produto com sucesso")
    void findAll_cenario01() {
        List<ProdutoPedido> lista = Arrays.asList(produtoPedido, new ProdutoPedido());
        when(produtoPedidoService.findAll()).thenReturn(lista);

        ResponseEntity<List<ProdutoPedido>> response = produtoPedidoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(produtoPedidoService, times(1)).findAll();
    }

    @Test
    @DisplayName("Retorna pedido do produto com id específico com sucesso")
    void findById_cenario01() {
        when(produtoPedidoService.findById(1)).thenReturn(produtoPedido);

        ResponseEntity<ProdutoPedido> response = produtoPedidoController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Checa de fato o valor dos ID de pedido e produto em produtoPedido
        assertEquals(1, response.getBody().getPedido().getId());
        assertEquals(10, response.getBody().getProduto().getId());
        verify(produtoPedidoService, times(1)).findById(1);
    }

    @Test
    @DisplayName("Atualiza pedido do produto com sucesso")
    void update_cenario01() {
        when(produtoPedidoService.update(produtoPedido.getId(), produtoPedido)).thenReturn(produtoPedido);

        ResponseEntity<String> response = produtoPedidoController.update(produtoPedido.getId(), produtoPedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(produtoPedidoService, times(1)).update(eq(1), any(ProdutoPedido.class));
    }

    @Test
    @DisplayName("Deleta pedido do produto com sucesso")
    void delete_cenario01() {
        doNothing().when(produtoPedidoService).delete(1);

        ResponseEntity<?> response = produtoPedidoController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(produtoPedidoService, times(1)).delete(1);
    }

}
