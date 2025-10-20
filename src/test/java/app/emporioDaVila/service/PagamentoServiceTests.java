package app.emporioDaVila.service;

import app.emporioDaVila.entity.Pagamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PagamentoServiceTests {

    @Autowired
    PagamentoService pagamentoService;

    //Teste com metodo save para válidar pagamento
    @Test
    void cenario01() {
        Pagamento pagamento = new Pagamento();
        pagamento.setTipo("Cartão");
        pagamento.setQuantidade(3);
        pagamento.setFinalizado(false);

        String retorno = this.pagamentoService.save(pagamento);
        assertEquals("Pagamento salvo com sucesso", retorno);
    }

    //Teste com metodo save para dar erro no pagamento
    @Test
    void cenario02() {
        Pagamento pagamento = new Pagamento();
        pagamento.setTipo("Cartão");
        pagamento.setQuantidade(null);
        pagamento.setFinalizado(false);

        assertThrows(Exception.class, () -> {
            String retorno = this.pagamentoService.save(pagamento);
        });
    }
}
