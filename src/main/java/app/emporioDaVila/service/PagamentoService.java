package app.emporioDaVila.service;

import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class PagamentoService {

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    private final PagamentoRepository pagamentoRepository;

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

    public Pagamento update(Integer id, Pagamento pagamento) {
        Pagamento update = findById(id);

        if (pagamento.getTipo() != null) {
            update.setTipo(pagamento.getTipo());
        }

        if (pagamento.getQuantidade() != 0) {
            update.setQuantidade(pagamento.getQuantidade());
        }

        if (pagamento.getEstado() != false) {
            update.setEstado(pagamento.getEstado());
        }

        return pagamentoRepository.save(update);
    }

    public void delete(Integer id) {
        Pagamento delete = findById(id);
        pagamentoRepository.delete(delete);
    }
}
