package ru.otus.hw.integration.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.hw.integration.domain.Chips;
import ru.otus.hw.integration.domain.Potato;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PotatoesServiceImpl implements PotatoesService {

    private static final String[] TYPES_OF_POTATOES = {"Russet", "Red Bliss", "Adirondack Blue", "Yukon Gold",
            "Purple Majesty", "Caribe Potatoes", "French Fingerling", "Purple Peruvian", "Beauregard Sweet",
            "Jewel Yams", "Japanese Sweet", "Garnet Yams"};

    private final ChipsManufactureGateway chipsManufactureGateway;

    public PotatoesServiceImpl(ChipsManufactureGateway chipsManufactureGateway) {
        this.chipsManufactureGateway = chipsManufactureGateway;
    }

    @Override
    public void startGeneratePotatoBagsLoop() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < 10; i++) {
            int num = i + 1;
            pool.execute(() -> {
                Collection<Potato> potatoesBag = generatePotatoesBag();
                log.info("{}, Incoming potato: {}", num,
                        potatoesBag.stream().map(Potato::getPotatoName)
                                .collect(Collectors.joining(",")));
                Collection<Chips> chips = chipsManufactureGateway.process(potatoesBag);

                if (chips != null) {
                    log.info("{}, Ready Chips: {}", num, chips.stream()
                            .map(Chips::getChipsTitle)
                            .collect(Collectors.joining(",")));
                } else {
                    log.info("The bag {}" + " contains the wrong TYPE of potatoes, the batch of chips {} is REJECTED",
                            num, num);
                }

            });
            delay();
        }
    }


    private static Potato generatePotato() {
        return new Potato(TYPES_OF_POTATOES[RandomUtils.nextInt(0, TYPES_OF_POTATOES.length)]);
    }

    private static Collection<Potato> generatePotatoesBag() {
        List<Potato> potatoes = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 5); ++i) {
            potatoes.add(generatePotato());
        }
        return potatoes;
    }

    private void delay() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}