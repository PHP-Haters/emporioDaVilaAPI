package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Enum.TipoPagamento;
import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.service.PagamentoService;
import app.emporioDaVila.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PagamentoControllerTests {

    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private PagamentoController pagamentoController;

    private Pagamento pagamento;

    //Teste para salvar pagamento com sucesso
    @Test
    void save_cenario01() {
        when(pagamentoService.save(pagamento)).thenReturn("Pagamento salvo com sucesso.");

        ResponseEntity<String> response = pagamentoController.save(pagamento);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário salvo com sucesso.", response.getBody());
    }

    //Teste para retornar lista de pagamentos
    @Test
    void findAll_cenario01() {
        List<Pagamento> lista = Arrays.asList(pagamento, new Pagamento());
        when(pagamentoService.findAll()).thenReturn(lista);

        ResponseEntity<List<Pagamento>> response = pagamentoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(pagamentoService, times(1)).findAll();
    }

    //Teste para retornar usuário único
    @Test
    void findById_cenario01() {
        when(pagamentoService.findById(1)).thenReturn(pagamento);

        ResponseEntity<Pagamento> response = pagamentoController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TipoPagamento.CARTAO_CREDITO, response.getBody().getTipo());
        verify(pagamentoService, times(1)).findById(1);
    }

    //Teste que verifica se o update foi chamado
    @Test
    void update_cenario01() {
        when(pagamentoService.update(pagamento.getId(), pagamento)).thenReturn(pagamento);

        ResponseEntity<String> response = pagamentoController.update(pagamento.getId(), pagamento);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(pagamentoService, times(1)).update(eq(1), any(Pagamento.class));
    }

    //Teste para deletar usuário
    @Test
    void delete_cenario01() {
        doNothing().when(pagamentoService).delete(1);

        ResponseEntity<?> response = pagamentoController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pagamentoService, times(1)).delete(1);
    }
}