package app.emporioDaVila.service;

import app.emporioDaVila.entity.ProdutoPedido;
import app.emporioDaVila.repository.ProdutoPedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoPedidoService {

    @Autowired
    private ProdutoPedidoRepository produtoPedidoRepository;

    public String save(ProdutoPedido produtoPedido) {
        this.produtoPedidoRepository.save(produtoPedido);
        return "ProdutoPedido salvo com sucesso";
    }

    public List<ProdutoPedido> findAll() {
        return produtoPedidoRepository.findAll();
    }

    public ProdutoPedido findById(Integer id) {
        return produtoPedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
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
