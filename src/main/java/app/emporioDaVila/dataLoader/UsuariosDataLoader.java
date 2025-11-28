package app.emporioDaVila.dataLoader;

import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuariosDataLoader {

    @Bean
    CommandLineRunner initUsuariosDatabase(UsuarioService usuarioService) {
        return args -> {
            // senha = admin
            Usuario admin = new Usuario("Admin", "admin", "45900000000", "admin@gmail.com", "ROLE_ADMIN");
            usuarioService.saveUsuario(admin);

            // senha = johnDoe
            Usuario tester = new Usuario("John Doe", "johnDoe", "45900000000", "johnDoe@gmail.com", "ROLE_USER");
            usuarioService.saveUsuario(tester);
        };
    }
}
