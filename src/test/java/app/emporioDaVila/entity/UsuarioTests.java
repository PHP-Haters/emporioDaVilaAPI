package app.emporioDaVila.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest não é necessário pois, nesse caso,
// o contexto inteiro da aplicação não precisa ser subido
public class UsuarioTests {
    @Test
    void prePersist_ShouldSetAdminToFalse_WhenAdminIsNull() {
        // Arrange: criar usuário sem definir admin
        Usuario usuario = new Usuario();
        usuario.setNome("Gabriel");
        usuario.setSenha("123456");
        usuario.setTelefone("11999999999");
        usuario.setEmail("gabriel@example.com");
        usuario.setAdmin(null);

        // Act: chamar o método @PrePersist
        usuario.prePersist();

        // Assert: verificar se admin foi definido como false
        assertNotNull(usuario.getAdmin(), "Admin não deve ser null após prePersist");
        assertFalse(usuario.getAdmin(), "Admin deve ser false se não definido antes do persist");
    }

    @Test
    void prePersist_ShouldNotChangeAdmin_WhenAdminIsAlreadySet() {
        // Arrange: criar usuário com admin = true
        Usuario usuario = new Usuario();
        usuario.setNome("Admin");
        usuario.setSenha("123456");
        usuario.setTelefone("11999999999");
        usuario.setEmail("admin@example.com");
        usuario.setAdmin(true);

        // Act
        usuario.prePersist();

        // Assert: admin não deve mudar
        assertTrue(usuario.getAdmin(), "Admin deve permanecer true se já definido");
    }
}
