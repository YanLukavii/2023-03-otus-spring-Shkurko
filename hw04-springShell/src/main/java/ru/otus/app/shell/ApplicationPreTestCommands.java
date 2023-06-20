package ru.otus.app.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.app.service.IOService;
import ru.otus.app.service.TestStart;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationPreTestCommands {

    private final IOService ioService;

    private final TestStart testStart;

    private int userAnswer;

    @ShellMethod(value = "Checking for the ability to count, 2 + 2 = ?", key = {"c", "ch", "che", "check"})
    public void check(@ShellOption(defaultValue = "0") int userAnswer) {
        this.userAnswer = userAnswer;
        ioService.print(String.format("Your Answer: %s", userAnswer));
    }

    @ShellMethod(value = "Start test", key = {"s", "st", "start", "start-test", "test", "t"})
    @ShellMethodAvailability(value = "isCheckPassed")
    public void startTest() {
        testStart.startTest();
        userAnswer = 0;
    }

    private Availability isCheckPassed() {
        return userAnswer == 4
                ? Availability.available()
                : Availability.unavailable("Take an arithmetic check before starting the test, 2 + 2 = ?");
    }
}