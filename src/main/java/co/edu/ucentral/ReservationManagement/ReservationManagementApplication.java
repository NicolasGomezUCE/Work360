package co.edu.ucentral.ReservationManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // <--- ¡Añadir esto!
public class ReservationManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationManagementApplication.class, args);
	}

}
