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
            Usuario admin = new Usuario("Admin", "$2a$10$xl4VocjQnVrw3.qWSuIgpurg4BoW0Voo2vZacUpPH1WwjY8SAEVcG", "45900000000", "admin@gmail.com", true);
            usuarioService.saveUsuario(admin);

            Usuario tester = new Usuario("Lucas", "@lucas2025", "45900000000", "lucas@gmail.com", false);
            usuarioService.saveUsuario(tester);
        };
    }
}
