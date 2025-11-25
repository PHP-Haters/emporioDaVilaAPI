    package app.emporioDaVila;

    import app.emporioDaVila.config.JwtProperties;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
    import org.springframework.boot.context.properties.EnableConfigurationProperties;

    @SpringBootApplication
    public class EmporioDaVilaApplication {

        public static void main(String[] args) {
            SpringApplication.run(EmporioDaVilaApplication.class, args);
        }
    }
