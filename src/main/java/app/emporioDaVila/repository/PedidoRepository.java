package app.emporioDaVila.repository;

import app.emporioDaVila.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    Pedido  findByEstadoAndIdUsuario(Boolean estado, Long  idUsuario);
}
