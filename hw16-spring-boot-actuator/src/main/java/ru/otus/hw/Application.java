package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args)  {
		SpringApplication.run(Application.class);
		System.out.printf("Чтобы перейти на страницу сайта открывай: %n%s%n",
				"http://localhost:8080");
		System.out.printf("Чтобы перейти на страницу actuator открывай: %n%s%n",
				"http://localhost:8080/actuator");
		System.out.printf("Чтобы перейти на страницу logfile открывай: %n%s%n",
				"http://localhost:8080/actuator/logfile");
	}

}