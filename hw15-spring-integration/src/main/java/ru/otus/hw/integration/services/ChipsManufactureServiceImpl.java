package ru.otus.hw.integration.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.integration.domain.Chips;
import ru.otus.hw.integration.domain.Potato;


@Service
@Slf4j
public class ChipsManufactureServiceImpl implements ChipsManufactureService {
    @Override
    public Chips cook(Potato potato) {

        log.info("Cooking {}", potato.getPotatoName());
        delay();
        log.info("Cooking {} done", potato.getPotatoName());


        return new Chips(potato.getPotatoName());
    }

    private static void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}