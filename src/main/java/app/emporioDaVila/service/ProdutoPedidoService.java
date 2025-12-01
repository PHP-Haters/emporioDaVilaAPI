package app.emporioDaVila.service;

import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.entity.ProdutoPedido;
import app.emporioDaVila.repository.ProdutoPedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoPedidoService {

    @Autowired
    private ProdutoPedidoRepository produtoPedidoRepository;

    public String save(ProdutoPedido produtoPedido) {
        try {
            this.produtoPedidoRepository.save(produtoPedido);
            return "Pedido do produto salvo com sucesso";
        }
        catch (DataIntegrityViolationException ex) {
            throw new GenericExceptions.InvalidData(
                    "Dados inválidos para o pedido do produto: " + ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new GenericExceptions.General(
                    "Erro inesperado ao salvar o pedido do produto: " + ex.getMessage()
            );
        }
    }

    public List<ProdutoPedido> findAll() {
        List<ProdutoPedido> produtoPedidos = produtoPedidoRepository.findAll();
        if (produtoPedidos.isEmpty()) {
            throw new GenericExceptions.General(
                    "Não existem produtos deste pedido cadastrados."
            );
        } else {
            return produtoPedidos;
        }
    }

    public List<ProdutoPedido> findByPedido(Integer idPedido) {
        List<ProdutoPedido> produtoPedidos = produtoPedidoRepository.findByPedido(idPedido);
        if (produtoPedidos.isEmpty()) {
            throw new GenericExceptions.General(
                    "Não existem produtos deste pedido cadastrados."
            );
        } else {
            return produtoPedidos;
        }
    }

    public ProdutoPedido findById(Integer id) {
        return produtoPedidoRepository.findById(id)
                .orElseThrow(() -> new GenericExceptions.NotFound("Produto do pedido não encontrado."));
    }

    public ProdutoPedido update(Integer id, ProdutoPedido novoProdutoPedido) {
        ProdutoPedido update = findById(id);

        if (novoProdutoPedido.getProduto() != null) {
            update.setProduto(novoProdutoPedido.getProduto());
        }

         if (novoProdutoPedido.getPedido() != null) {
             update.setPedido(novoProdutoPedido.getPedido());
        }

        return produtoPedidoRepository.save(update);
    }

    public void delete(Integer id) {
        ProdutoPedido delete = findById(id);
        produtoPedidoRepository.delete(delete);
    }
}
