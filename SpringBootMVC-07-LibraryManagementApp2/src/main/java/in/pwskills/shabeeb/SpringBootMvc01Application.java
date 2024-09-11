package in.pwskills.shabeeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"in.pwskills.shabeeb.controller"})
public class SpringBootMvc01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMvc01Application.class, args);
	}

}
