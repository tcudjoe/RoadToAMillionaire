package hibera.api.rtmwebappapi;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import hibera.api.rtmwebappapi.Auth.RegisterRequest;
import hibera.api.rtmwebappapi.Auth.service.AuthService;
import hibera.api.rtmwebappapi.users.service.CompanyService;
import hibera.api.rtmwebappapi.users.dto.CreateCompanyRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static hibera.api.rtmwebappapi.domain.enums.Role.*;

@SpringBootApplication
@EnableJpaRepositories
public class RtmWebAppApiApplication {


	public static void main(String[] args) throws MailjetSocketTimeoutException, MailjetException {
		SpringApplication.run(RtmWebAppApiApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(
			AuthService service,
			CompanyService companyService
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

			var company = CreateCompanyRequest.builder()
					.company_name("NO COMPANY")
					.company_address("NO COMPANY")
					.company_btw_number("NO COMPANY")
					.company_email("NO COMPANY")
					.company_kvk_number("NO COMPANY")
					.company_phonenumber("NO COMPANY")
					.build();

			companyService.createCompany(company);
			service.register(admin);
			service.register(manager);
			service.register(user);
		};
	}

}
