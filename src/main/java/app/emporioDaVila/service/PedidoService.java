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

    public Pedido updateState(Integer id, Pedido novoPedido) {
        Pedido update = findById(id);

        if (novoPedido.getEstado() != false) {
            update.setEstado(novoPedido.getEstado());
        }

        return pedidoRepository.save(update);
    }

    public void delete(Integer id) {
        Pedido delete = findById(id);
        pedidoRepository.delete(delete);
    }
}
