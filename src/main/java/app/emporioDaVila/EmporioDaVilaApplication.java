package app.emporioDaVila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("app.emporioDaVila.config")
public class EmporioDaVilaApplication {

	public static void main(String[] args) {
        SpringApplication.run(EmporioDaVilaApplication.class, args);
	}

}
