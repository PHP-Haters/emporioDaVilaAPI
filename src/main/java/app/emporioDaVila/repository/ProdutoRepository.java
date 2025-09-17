package app.emporioDaVila.repository;

import app.emporioDaVila.entity.Enum.Categoria;
import app.emporioDaVila.entity.Produto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<Produto> findAllByCategoria(Categoria categoria);
}
