package components;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"components/config", "components/models", "components/repositories", "components/services", "components/controllers","components/jwt"})
public class ZuPointsCatalisa5Application {

	public static void main(String[] args) {
		SpringApplication.run(ZuPointsCatalisa5Application.class, args);
	}

}
