package components;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"components/config", "components/models", "components/repositories", "components/services", "components/controllers","components/jwt"})
@OpenAPIDefinition(info = @Info(title = "ZupHall API", version = "1", description = "Api desenvolvida para incentivar o reconhecimento entre colaboradores"))
public class ZuPointsCatalisa5Application {

	public static void main(String[] args) {
		SpringApplication.run(ZuPointsCatalisa5Application.class, args);
	}

}
