package au.com.riosoftware.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "au.com.riosoftware.accounts.repository")
public class Application {

	static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
