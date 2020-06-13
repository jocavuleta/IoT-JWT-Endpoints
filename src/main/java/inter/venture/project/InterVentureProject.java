package inter.venture.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class InterVentureProject {

	public static void main(String[] args) {
		SpringApplication.run(InterVentureProject.class, args);
	}

}
