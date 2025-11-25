package app.emporioDaVila.controller;

import app.emporioDaVila.config.JwtAuthenticationFilter;
import app.emporioDaVila.config.JwtServiceGenerator;
import app.emporioDaVila.entity.Enum.Categoria;
import app.emporioDaVila.entity.Pedido;
import app.emporioDaVila.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTests {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private Pedido pedido;

    @BeforeEach
    void setup() {
        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setIdUsuario(1L);
        pedido.setEstado(true);
    }

    @Test
    void save_cenarioSucesso() {
        when(pedidoService.save(pedido)).thenReturn("pedido salvo com sucesso.");

        ResponseEntity<String> response = pedidoController.save(pedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pedido salvo com sucesso.", response.getBody());
        verify(pedidoService, times(1)).save(pedido);
    }

    @Test
    void findAll_cenarioSucesso() {
        List<Pedido> lista = Arrays.asList(pedido, new Pedido());
        when(pedidoService.findAll()).thenReturn(lista);

        ResponseEntity<List<Pedido>> response = pedidoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(pedidoService, times(1)).findAll();
    }

    @Test
    void findById_cenarioSucesso() {
        when(pedidoService.findById(1)).thenReturn(pedido);

        ResponseEntity<Pedido> response = pedidoController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(pedidoService, times(1)).findById(1);
    }

    @Test
    void update_cenarioSucesso() {
        when(pedidoService.updateState(Math.toIntExact(pedido.getId()), pedido)).thenReturn(pedido);

        ResponseEntity<String> response = pedidoController.update(1, pedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pedido atualizado com sucesso.", response.getBody());
        verify(pedidoService, times(1)).updateState(eq(1), any(Pedido.class));
    }

    @Test
    void delete_cenarioSucesso() {
        doNothing().when(pedidoService).delete(1);

        ResponseEntity<?> response = pedidoController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pedidoService, times(1)).delete(1);
    }

    }
