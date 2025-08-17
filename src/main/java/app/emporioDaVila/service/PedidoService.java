package app.emporioDaVila.service;


import app.emporioDaVila.entity.Pedido;
import app.emporioDaVila.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public String save(Pedido pedido) {
        this.pedidoRepository.save(pedido);
        return "Pedido salvo com sucesso";
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Pedido update(Integer id, Pedido novoPedido) {
        Pedido update = findById(id);

        if (novoPedido.getTipo() != null) {
            update.setTipo(novoPedido.getTipo());
        }

        if (novoPedido.getQuantidade() != 0) {
            update.setQuantidade(novoPedido.getQuantidade());
        }

        if (novoPedido.getFinalizado() != false) {
            update.setFinalizado(novoPedido.getFinalizado());
        }

        return pedidoRepository.save(update);
    }

    public void delete(Integer id) {
        Pedido delete = findById(id);
        pedidoRepository.delete(delete);
    }
}
