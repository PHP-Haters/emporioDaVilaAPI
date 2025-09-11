package app.emporioDaVila.service;

import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {
    
    @Autowired
    private PagamentoRepository pagamentoRepository;
    
    public String save(Pagamento pagamento) {
        try {
            pagamentoRepository.save(pagamento);
            return "Pagamento salvo com sucesso";
        }
        catch (DataIntegrityViolationException ex) {
            throw new GenericExceptions.InvalidData(
                    "Dados inválidos para o pagamento: " + ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new GenericExceptions.General(
                    "Erro inesperado ao salvar o pagamento: " + ex.getMessage()
            );
        }
    }

    public List<Pagamento> findAll() {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        if (pagamentos.isEmpty()) {
            throw new GenericExceptions.General(
                    "Não existem pagamentos cadastrados."
            );
        } else {
            return pagamentos;
        }
    }

    public Pagamento findById(Integer id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new GenericExceptions.NotFound("Pagamento não encontrado."));
    }

    public Pagamento update(Integer id, Pagamento novoPagamento) {
        Pagamento update = findById(id);

        if (novoPagamento.getTipo() != null) {
            update.setTipo(novoPagamento.getTipo());
        }

        if (novoPagamento.getQuantidade() != 0) {
            update.setQuantidade(novoPagamento.getQuantidade());
        }

        if (novoPagamento.getFinalizado() != false) {
            update.setFinalizado(novoPagamento.getFinalizado());
        }

        return pagamentoRepository.save(update);
    }

    public void delete(Integer id) {
        Pagamento delete = findById(id);
        pagamentoRepository.delete(delete);
    }
}
