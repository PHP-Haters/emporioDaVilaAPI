package app.emporioDaVila.service;

import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
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
import org.springframework.dao.DataIntegrityViolationException;

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

    @Test
    @DisplayName("Salva pagamento com sucesso")
    void save_cenario01() {
        Pagamento pagamento = new Pagamento();
        pagamento.setTipo(TipoPagamento.CARTAO_CREDITO);
        pagamento.setQuantidade(3);
        pagamento.setFinalizado(false);

        String retorno = this.pagamentoService.save(pagamento);
        assertEquals("Pagamento salvo com sucesso", retorno);
    }

    @Test
    @DisplayName("Lança exceção ao salvar pagamento com dados inválidos")
    void save_cenario02() {
        Pagamento pagamentoInvalido = new Pagamento();

        when(pagamentoRepository.save(pagamentoInvalido))
                .thenThrow(new DataIntegrityViolationException("Campo obrigatório ausente"));

        GenericExceptions.InvalidData ex = assertThrows(
                GenericExceptions.InvalidData.class,
                () -> pagamentoService.save(pagamentoInvalido)
        );

        assertTrue(ex.getMessage().contains("Dados inválidos para o pagamento"));
        verify(pagamentoRepository, times(1)).save(pagamentoInvalido);
    }

    @Test
    @DisplayName("Lança exceção genérica ao salvar pagamento")
    void save_cenario03() {
        Pagamento pagamentoErro = new Pagamento();

        when(pagamentoRepository.save(pagamentoErro))
                .thenThrow(new RuntimeException("Falha inesperada no banco"));

        GenericExceptions.General ex = assertThrows(
                GenericExceptions.General.class,
                () -> pagamentoService.save(pagamentoErro)
        );

        assertTrue(ex.getMessage().contains("Erro inesperado ao salvar o pagamento"));
    }

    @Test
    @DisplayName("Retorna lista de pagamentos com sucesso")
    void findAll_cenario01() {
        List<Pagamento> lista = new ArrayList<>();
        lista.add(pagamento);

        when(pagamentoRepository.findAll()).thenReturn(lista);

        List<Pagamento> resultado = pagamentoService.findAll();

        assertEquals(1, resultado.size());
        assertEquals(pagamento.getTipo(), resultado.get(0).getTipo());
    }

    @Test
    @DisplayName("Lança exceção ao tentar buscar lista de pagamentos vazia")
    void findAll_cenario02() {
        when(pagamentoRepository.findAll()).thenReturn(new ArrayList<>());

        GenericExceptions.General ex = assertThrows(
                GenericExceptions.General.class,
                () -> pagamentoService.findAll()
        );

        assertTrue(ex.getMessage().contains("Não existem pagamentos cadastrados"));
    }

    @Test
    @DisplayName("Retorna pagamento com id específico com sucesso")
    void findById_cenario01() {
        when(pagamentoRepository.findById(1)).thenReturn(Optional.of(pagamento));

        Pagamento resultado = pagamentoService.findById(1);

        assertEquals(pagamento.getTipo(), resultado.getTipo());
    }

    @Test
    @DisplayName("Lança exceção ao buscar pagamento inexistente")
    void findById_cenario02() {
        when(pagamentoRepository.findById(99)).thenReturn(Optional.empty());

        GenericExceptions.NotFound ex = assertThrows(
                GenericExceptions.NotFound.class,
                () -> pagamentoService.findById(99)
        );

        assertTrue(ex.getMessage().contains("Pagamento não encontrado"));
    }


    @Test
    @DisplayName("Atualiza pagamento com sucesso")
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

    @Test
    @DisplayName("Não atualiza campos quando todos os valores novos são nulos ou falsos")
    void update_cenario02() {
        Pagamento novoPagamento = new Pagamento();
        novoPagamento.setTipo(null);
        novoPagamento.setQuantidade(0);
        novoPagamento.setFinalizado(false);

        when(pagamentoRepository.findById(1)).thenReturn(Optional.of(pagamento));
        when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pagamento resultado = pagamentoService.update(1, novoPagamento);

        // Verifica que nada mudou
        assertEquals(pagamento.getTipo(), resultado.getTipo());
        assertEquals(pagamento.getQuantidade(), resultado.getQuantidade());
        assertEquals(pagamento.getFinalizado(), resultado.getFinalizado());
    }


    @Test
    @DisplayName("Deleta pagamento com sucesso")
    void delete_cenario01() {
        when(pagamentoRepository.findById(1)).thenReturn(Optional.of(pagamento));
        doNothing().when(pagamentoRepository).delete(pagamento);

        assertDoesNotThrow(() -> pagamentoService.delete(1));
        verify(pagamentoRepository, times(1)).delete(pagamento);
    }

    @Test
    @DisplayName("Lança exceção ao tentar deletar pagamento inexistente")
    void delete_cenario02() {
        when(pagamentoRepository.findById(99)).thenReturn(Optional.empty());

        GenericExceptions.NotFound ex = assertThrows(
                GenericExceptions.NotFound.class,
                () -> pagamentoService.delete(99)
        );

        assertTrue(ex.getMessage().contains("Pagamento não encontrado"));
    }
}
