package ru.otus.hw.integration.services;


import java.util.Collection;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.integration.domain.Chips;
import ru.otus.hw.integration.domain.Potato;

@MessagingGateway
public interface ChipsManufactureGateway {

	@Gateway(requestChannel = "potatoChannel", replyChannel = "chipsChannel")
	Collection<Chips> process(Collection<Potato> potatoes);

}