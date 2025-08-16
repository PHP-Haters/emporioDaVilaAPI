package app.emporioDaVila.service;

import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public String save(Pagamento pagamento) {
        this.pagamentoRepository.save(pagamento);
        return "Pagamento salvo com sucesso";
    }

    public List<Pagamento> findAll() {
        return pagamentoRepository.findAll();
    }

    public Pagamento findById(Integer id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
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
