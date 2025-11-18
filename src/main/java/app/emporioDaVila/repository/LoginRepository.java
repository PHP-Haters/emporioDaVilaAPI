package app.emporioDaVila.repository;

import java.util.Optional;

import app.emporioDaVila.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Usuario, Long>{
	public Optional<Usuario> findByEmail(String login);
}
