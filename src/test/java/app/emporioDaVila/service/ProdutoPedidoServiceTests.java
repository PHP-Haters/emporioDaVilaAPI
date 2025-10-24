package app.emporioDaVila.service;

import app.emporioDaVila.entity.Pedido;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.entity.ProdutoPedido;
import app.emporioDaVila.repository.ProdutoPedidoRepository;
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

    //Teste com metodo save para válidar pagamento
    @Test
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

    //Teste com metodo findAll para procurar todos os pagamentos
    @Test
    void findAll_cenario02() {
        List<ProdutoPedido> lista = new ArrayList<>();
        lista.add(produtoPedido);

        when(produtoPedidoRepository.findAll()).thenReturn(lista);

        List<ProdutoPedido> resultado = produtoPedidoRepository.findAll();

        assertEquals(1, resultado.size());
        assertEquals(produtoPedido.getId(), resultado.get(0).getId());
    }

    //Teste com metodo findById para procurar algum pagamento específico
    @Test
    void findById_cenario01() {
        when(produtoPedidoRepository.findById(1)).thenReturn(Optional.of(produtoPedido));

        ProdutoPedido resultado = produtoPedidoService.findById(1);

        assertEquals(produtoPedido.getId(), resultado.getId());
    }

    //Teste com metodo update para atualizar pagamento
    @Test
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

    //Teste com metodo delete para deletar pagamento
    @Test
    void delete_cenario01() {
        when(produtoPedidoRepository.findById(1)).thenReturn(Optional.of(produtoPedido));
        doNothing().when(produtoPedidoRepository).delete(produtoPedido);

        assertDoesNotThrow(() -> produtoPedidoService.delete(1));
        verify(produtoPedidoRepository, times(1)).delete(produtoPedido);
    }

}
