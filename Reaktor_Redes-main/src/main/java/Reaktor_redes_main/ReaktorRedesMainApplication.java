package Reaktor_redes_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@ComponentScan(basePackages = {"Reaktor_redes_main", "es.iesjandula"})
@EnableMethodSecurity
public class ReaktorRedesMainApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ReaktorRedesMainApplication.class, args);
	}

}
