package hibera.api.rtmwebappapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RtmWebAppApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RtmWebAppApiApplication.class, args);
	}

}
