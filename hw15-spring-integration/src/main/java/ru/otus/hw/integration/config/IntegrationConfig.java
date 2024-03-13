package ru.otus.hw.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

import ru.otus.hw.integration.domain.Chips;
import ru.otus.hw.integration.domain.Potato;
import ru.otus.hw.integration.services.ChipsManufactureService;

@Configuration
public class IntegrationConfig {

	@Bean
	public MessageChannelSpec<?, ?> potatoChannel() {
		return MessageChannels.queue(10);
	}

	@Bean
	public MessageChannelSpec<?, ?> chipsChannel() {
		return MessageChannels.publishSubscribe();
	}


	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	public PollerSpec poller() {
		return Pollers.fixedRate(100).maxMessagesPerPoll(2);
	}

	@Bean
	public IntegrationFlow chipsManufactureFlow(ChipsManufactureService chipsManufactureService) {
		return IntegrationFlow.from(potatoChannel())
				.split()
				.<Potato>filter(p -> !p.getPotatoName().equals("Russet"))
				.<Potato>filter(p -> !p.getPotatoName().equals("Adirondack Blue"))
				.handle(chipsManufactureService, "cook")
				.<Chips, Chips>transform(f -> new Chips(f.getChipsTitle().toUpperCase()))
				.aggregate()
				.channel(chipsChannel())
				.get();
	}
}