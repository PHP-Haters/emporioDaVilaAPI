package app.emporioDaVila.repository;

import app.emporioDaVila.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByUsuarioId(Long usuarioId);
}
