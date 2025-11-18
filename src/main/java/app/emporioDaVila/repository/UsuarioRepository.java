package app.emporioDaVila.repository;

import app.emporioDaVila.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpGE;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNome(String nome);
}
