package hibera.api.rtmwebappapi;

import hibera.api.rtmwebappapi.Auth.RegisterRequest;
import hibera.api.rtmwebappapi.Auth.service.AuthenticationService;
import hibera.api.rtmwebappapi.domain.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static hibera.api.rtmwebappapi.domain.Role.*;

@SpringBootApplication
@EnableJpaRepositories
public class RtmWebAppApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RtmWebAppApiApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	){

		return args -> {
//			var admin = RegisterRequest.builder()
//					.firstName("Tyra")
//					.lastName("Cudjoe")
//					.email("admin@roadtoamillionaire.com")
//					.password("test1234")
//					.role(ADMIN)
//					.build();
//			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.firstName("Malcolm")
					.lastName("Cudjoe")
					.email("manager@roadtoamillionaire.com")
					.password("test1234")
					.role(MANAGER)
					.build();
			System.out.println("Manager token: " + service.register(manager).getAccessToken());
//
//			var user = RegisterRequest.builder()
//					.firstName("Albert")
//					.lastName("Cudjoe")
//					.email("user@roadtoamillionaire.com")
//					.password("test1234")
//					.role(USER)
//					.build();
//			System.out.println("User token: " + service.register(user).getAccessToken());
		};
	}

}
