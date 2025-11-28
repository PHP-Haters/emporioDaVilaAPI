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
            Usuario admin = new Usuario("Admin", "$2a$12$wKv.F56KNmOWsY9ohozbuuMUey6s5TMBB1A7zlgeBBXu9Om0X0XAu", "45900000000", "admin@gmail.com", "ROLE_ADMIN");
            usuarioService.saveUsuario(admin);

            // senha = johnDoe
            Usuario tester = new Usuario("John Doe", "$2a$12$rN4kDek4S3u9bcv6q1ML9.jsAILz6Wx4Hdu5kwZ3Ld3Mi2Becx1ry", "45900000000", "johnDoe@gmail.com", "ROLE_USER");
            usuarioService.saveUsuario(tester);
        };
    }
}
