package app.emporioDaVila.service;

import app.emporioDaVila.entity.Enum.TipoPagamento;
import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTests {

    @Mock
    PagamentoRepository pagamentoRepository;

    @InjectMocks
    PagamentoService pagamentoService;

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

    //Teste com metodo save para dar erro no pagamento
    @Test
    void save_cenario02() {
        Pagamento pagamento = new Pagamento();
        pagamento.setTipo(TipoPagamento.CARTAO_CREDITO);
        pagamento.setQuantidade(null);
        pagamento.setFinalizado(false);

        assertThrows(Exception.class, () -> {
            String retorno = this.pagamentoService.save(pagamento);
        });
    }
}
