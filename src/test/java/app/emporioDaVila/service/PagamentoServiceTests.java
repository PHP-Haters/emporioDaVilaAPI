package app.emporioDaVila.service;

import app.emporioDaVila.entity.Enum.TipoPagamento;
import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.entity.PagamentoPedido;
import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.repository.PagamentoRepository;
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
public class PagamentoServiceTests {

    @Mock
    PagamentoRepository pagamentoRepository;

    @InjectMocks
    PagamentoService pagamentoService;

    Pagamento pagamento;
    Pagamento pagamentoQuebrado;

    @BeforeEach
    void setup() {
        pagamento = new Pagamento();
        pagamento.setId(1);
        pagamento.setTipo(TipoPagamento.CARTAO_CREDITO);
        pagamento.setQuantidade(2);
        pagamento.setFinalizado(true);

        List<PagamentoPedido> pagamentoPedidos = new ArrayList<>();

        pagamento.setPagamentoPedidos(pagamentoPedidos);

        pagamentoQuebrado = new Pagamento();
        pagamentoQuebrado.setId(2);
        pagamentoQuebrado.setTipo(TipoPagamento.DINHEIRO);
        pagamentoQuebrado.setQuantidade(null);
        pagamentoQuebrado.setFinalizado(null);
        pagamentoQuebrado.setPagamentoPedidos(null);
    }

    //Teste com metodo save para válidar pagamento
    @Test
    void save_cenario01() {
        Pagamento pagamento = new Pagamento();
        pagamento.setTipo(TipoPagamento.CARTAO_CREDITO);
        pagamento.setQuantidade(3);
        pagamento.setFinalizado(false);

        String retorno = this.pagamentoService.save(pagamento);
        assertEquals("Pagamento salvo com sucesso", retorno);
    }

    //Teste com metodo findAll para procurar todos os pagamentos
    @Test
    @DisplayName("")
    void findAll_cenario02() {
        List<Pagamento> lista = new ArrayList<>();
        lista.add(pagamento);

        when(pagamentoRepository.findAll()).thenReturn(lista);

        List<Pagamento> resultado = pagamentoRepository.findAll();

        assertEquals(1, resultado.size());
        assertEquals(pagamento.getTipo(), resultado.get(0).getTipo());
    }

    //Teste com metodo findById para procurar algum pagamento específico
    @Test
    void findById_cenario01() {
        when(pagamentoRepository.findById(1)).thenReturn(Optional.of(pagamento));

        Pagamento resultado = pagamentoService.findById(1);

        assertEquals(pagamento.getTipo(), resultado.getTipo());
    }

    //Teste com metodo update para atualizar pagamento
    @Test
    void update_cenario01() {
        Pagamento novoPagamento = new Pagamento();
        novoPagamento.setTipo(TipoPagamento.DINHEIRO);
        novoPagamento.setQuantidade(5);
        novoPagamento.setFinalizado(true);

        when(pagamentoRepository.findById(1)).thenReturn(Optional.of(pagamento));
        when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pagamento resultado = pagamentoService.update(1, novoPagamento);

        assertEquals(TipoPagamento.DINHEIRO, resultado.getTipo());
        assertEquals(5, resultado.getQuantidade());
        assertTrue(resultado.getFinalizado());
        verify(pagamentoRepository, times(1)).save(any(Pagamento.class));
    }


    //Teste com metodo delete para deletar pagamento
    @Test
    void delete_cenario01() {
        when(pagamentoRepository.findById(1)).thenReturn(Optional.of(pagamento));
        doNothing().when(pagamentoRepository).delete(pagamento);

        assertDoesNotThrow(() -> pagamentoService.delete(1));
        verify(pagamentoRepository, times(1)).delete(pagamento);
    }

}
