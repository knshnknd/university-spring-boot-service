package application;

import application.services.DataPreloadService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication
public class UniversitySpringBootServiceApplication {

	@Autowired
	private DataPreloadService dataPreloadService;

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		dataPreloadService.preloadData();
	}

	public static void main(String[] args) {
		SpringApplication.run(UniversitySpringBootServiceApplication.class, args);
	}

}
