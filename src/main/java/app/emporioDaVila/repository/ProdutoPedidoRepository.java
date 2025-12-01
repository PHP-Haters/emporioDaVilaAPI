package app.emporioDaVila.repository;

import app.emporioDaVila.entity.Pedido;
import app.emporioDaVila.entity.ProdutoPedido;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Integer> {
    List<ProdutoPedido> findByPedido(Integer idPedido);
}
