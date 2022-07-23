package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class UniversitySpringBootServiceApplication {

	// Объект для загрузки данных: создаются: группы, студенты, места обучения, дисциплины, преподаватели
	// и по 3 занятия на каждого студента на месяц с 22.07.2022 по 22.08.2022
	// Удалить при реальном использовании приложения!

	@Autowired
	private DataPreloadingObject dataPreloadingObject;

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		dataPreloadingObject.preloadData();
	}

	public static void main(String[] args) {
		SpringApplication.run(UniversitySpringBootServiceApplication.class, args);
	}

}
