package ru.otus.app.commandlinerunners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.app.service.TestStart;

@Component
public class PreparationDev implements CommandLineRunner {

    private final TestStart testStart;

    public PreparationDev(TestStart testStart) {
        this.testStart = testStart;
    }

    @Override
    public void run(String... args) {
        testStart.startTest();
    }
}
