package application;

import application.services.DataPreloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class UniversitySpringBootServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(UniversitySpringBootServiceApplication.class, args);
		DataPreloadService dataPreloadService = context.getBean("dataPreloadService", DataPreloadService.class);

		dataPreloadService.preloadData();
	}

}
