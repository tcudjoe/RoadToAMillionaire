package hibera.api.rtmwebappapi;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import hibera.api.rtmwebappapi.domain.dto.RegisterRequest;
import hibera.api.rtmwebappapi.Auth.service.AuthService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static hibera.api.rtmwebappapi.domain.Role.*;

@SpringBootApplication
@EnableJpaRepositories
public class RtmWebAppApiApplication {


	public static void main(String[] args) throws MailjetSocketTimeoutException, MailjetException {
		SpringApplication.run(RtmWebAppApiApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(
			AuthService service
	){

		return args -> {
			var admin = RegisterRequest.builder()
					.firstName("Tyra")
					.lastName("Cudjoe")
					.email("admin@roadtoamillionaire.com")
					.password("test1234")
					.role(ADMIN)
					.build();

			var manager = RegisterRequest.builder()
					.firstName("Malcolm")
					.lastName("Cudjoe")
					.email("manager@roadtoamillionaire.com")
					.password("test1234")
					.role(MANAGER)
					.build();

			var user = RegisterRequest.builder()
					.firstName("Albert")
					.lastName("Cudjoe")
					.email("user@roadtoamillionaire.com")
					.password("test1234")
					.role(USER)
					.build();
		};
	}

}
