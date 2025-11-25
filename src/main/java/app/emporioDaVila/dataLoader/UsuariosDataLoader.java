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
            Usuario admin = new Usuario("Admin", "$2a$10$xl4VocjQnVrw3.qWSuIgpurg4BoW0Voo2vZacUpPH1WwjY8SAEVcG", "45900000000", "admin@gmail.com", "ROLE_ADMIN");
            usuarioService.saveUsuario(admin);

            Usuario tester = new Usuario("John Doe", "$2a$10$u.EXiV5UftSNwDrT1krFUuyFN/L99EK7okSrVJU4vRpla6veyPMKy", "45900000000", "johnDoe@gmail.com", "ROLE_USER");
            usuarioService.saveUsuario(tester);
        };
    }
}
