package app.emporioDaVila.controller;

import app.emporioDaVila.config.JwtAuthenticationFilter;
import app.emporioDaVila.config.JwtServiceGenerator;
import app.emporioDaVila.entity.*;
import app.emporioDaVila.entity.Enum.TipoPagamento;
import app.emporioDaVila.service.PagamentoService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PagamentoControllerTests {

    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private PagamentoController pagamentoController;

    Pagamento pagamento;

    @BeforeEach
    void setup() {
        pagamento = new Pagamento();
        pagamento.setId(1);
        pagamento.setTipo(TipoPagamento.CARTAO_CREDITO);
        pagamento.setQuantidade(2);
        pagamento.setFinalizado(true);

        List<PagamentoPedido> pagamentoPedidos = new ArrayList<>();
        pagamento.setPagamentoPedidos(pagamentoPedidos);
    }

    @Test
    @DisplayName("Salva pagamento com sucesso")
    void save_cenario01() {
        when(pagamentoService.save(pagamento)).thenReturn("Pagamento salvo com sucesso");

        ResponseEntity<String> response = pagamentoController.save(pagamento);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pagamento salvo com sucesso", response.getBody());
        verify(pagamentoService, times(1)).save(pagamento);
    }

    @Test
    @DisplayName("Retorna lista de pagamentos com sucesso")
    void findAll_cenario01() {
        List<Pagamento> lista = Arrays.asList(pagamento, new Pagamento());
        when(pagamentoService.findAll()).thenReturn(lista);

        ResponseEntity<List<Pagamento>> response = pagamentoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(pagamentoService, times(1)).findAll();
    }

    @Test
    @DisplayName("Retorna pagamento com id específico com sucesso")
    void findById_cenario01() {
        when(pagamentoService.findById(1)).thenReturn(pagamento);

        ResponseEntity<Pagamento> response = pagamentoController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TipoPagamento.CARTAO_CREDITO, response.getBody().getTipo());
        verify(pagamentoService, times(1)).findById(1);
    }

    @Test
    @DisplayName("Atualiza pagamento com sucesso")
    void update_cenario01() {
        when(pagamentoService.update(pagamento.getId(), pagamento)).thenReturn(pagamento);

        ResponseEntity<String> response = pagamentoController.update(pagamento.getId(), pagamento);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(pagamentoService, times(1)).update(eq(1), any(Pagamento.class));
    }

    @Test
    @DisplayName("Deleta pagamento com sucesso")
    void delete_cenario01() {
        doNothing().when(pagamentoService).delete(1);

        ResponseEntity<?> response = pagamentoController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pagamentoService, times(1)).delete(1);
    }

}
