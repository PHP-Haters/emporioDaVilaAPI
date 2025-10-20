package app.emporioDaVila.entity;

import app.emporioDaVila.entity.Enum.TipoPagamento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PagamentoTests {

    @Test
    void prePersist_cenario01() {
        // Pagamento com atributo "finalizado" nulo deve ser setado para falso

        // Arrange: criar pagamento sem definir finalizado
        Pagamento pagamento = new Pagamento();
        pagamento.setTipo(TipoPagamento.CARTAO_CREDITO);
        pagamento.setQuantidade(2);
        pagamento.setFinalizado(null);

        // Act: chamar o método @PrePersist
        pagamento.prePersist();

        // Assert: verificar se finalizado foi definido como true
        assertNotNull(pagamento.getFinalizado(), "Finalizado não deve ser null após prePersist");
        assertFalse(pagamento.getFinalizado(), "Finalizado deve ser falso se não definido antes do persist");
    }

    @Test
    void prePersist_cenario02() {
        // Finalizado true sendo persistido

        // Arrange: criar pagamento com finalizado = true
        Pagamento pagamento = new Pagamento();
        pagamento.setTipo(TipoPagamento.DINHEIRO);
        pagamento.setQuantidade(1);
        pagamento.setFinalizado(true);

        // Act
        pagamento.prePersist();

        // Assert: finalizado não deve mudar
        assertTrue(pagamento.getFinalizado(), "Finalizado deve permanecer true se já definido");
    }

    @Test
    void prePersist_cenario03() {
        // Finalizado false sendo persistido

        // Arrange: criar pagamento com finalizado = false
        Pagamento pagamento = new Pagamento();
        pagamento.setTipo(TipoPagamento.DINHEIRO);
        pagamento.setQuantidade(1);
        pagamento.setFinalizado(false);

        // Act
        pagamento.prePersist();

        // Assert: finalizado não deve mudar
        assertFalse(pagamento.getFinalizado(), "Finalizado deve permanecer false se já definido");
    }
}
