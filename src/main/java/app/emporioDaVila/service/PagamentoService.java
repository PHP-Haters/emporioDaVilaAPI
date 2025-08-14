package app.emporioDaVila.service;

import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;

public class PagamentoService {

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    private final PagamentoRepository pagamentoRepository;

    public Pagamento findById(Integer id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }
}
