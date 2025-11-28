package app.emporioDaVila.entity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTests {

    @Test
    void prePersist_deveDefinirRolePadrao() {
        Usuario usuario = new Usuario();
        usuario.setNome("João");
        usuario.setSenha("123456");
        usuario.setTelefone("11999999999");
        usuario.setEmail("joao@example.com");

        usuario.prePersist();

        assertEquals("ROLE_USER", usuario.getRole());
    }

    @Test
    void prePersist_naoDeveAlterarRoleSeJaDefinida() {
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setSenha("123456");
        usuario.setTelefone("11999999999");
        usuario.setEmail("maria@example.com");
        usuario.setRole("ROLE_ADMIN");

        usuario.prePersist();

        assertEquals("ROLE_ADMIN", usuario.getRole());
    }
}
