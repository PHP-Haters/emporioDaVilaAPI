package app.emporioDaVila.repository;

import app.emporioDaVila.entity.ProdutoPedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Integer> {
}
