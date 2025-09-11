package app.emporioDaVila.service;


import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
import app.emporioDaVila.entity.Pedido;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public String save(Pedido pedido) {
        try {
            pedidoRepository.save(pedido);
            return "Pedido salvo com sucesso";
        }
        catch (DataIntegrityViolationException ex) {
            throw new GenericExceptions.InvalidData(
                    "Dados inválidos para o pedido: " + ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new GenericExceptions.General(
                    "Erro inesperado ao salvar o pedido: " + ex.getMessage()
            );
        }
    }

    public List<Pedido> findAll() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        if (pedidos.isEmpty()) {
            throw new GenericExceptions.General(
                    "Não existem pedidos cadastrados."
            );
        } else {
            return pedidos;
        }
    }

    public Pedido findById(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new GenericExceptions.NotFound("Pedido não encontrado."));
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
