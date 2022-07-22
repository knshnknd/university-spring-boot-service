package application;

import application.services.DataPreloadService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication
public class UniversitySpringBootServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(UniversitySpringBootServiceApplication.class, args);
		// DataPreloadService dataPreloadService = context.getBean("dataPreloadService", DataPreloadService.class);

		// dataPreloadService.preloadData();
	}

}
