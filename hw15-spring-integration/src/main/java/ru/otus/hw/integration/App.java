package ru.otus.hw.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;
import ru.otus.hw.integration.services.PotatoesService;

@Slf4j
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);

		PotatoesService potatoesService = ctx.getBean(PotatoesService.class);
		potatoesService.startGeneratePotatoBagsLoop();
	}
}