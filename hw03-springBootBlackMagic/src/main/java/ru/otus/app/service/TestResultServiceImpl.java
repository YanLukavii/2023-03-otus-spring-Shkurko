package ru.otus.app.service;

import org.springframework.stereotype.Service;
import ru.otus.app.configs.providers.LocalizationSettingsProvider;
import ru.otus.app.configs.providers.SettingsProvider;
import ru.otus.app.domain.Student;

@Service
public class TestResultServiceImpl implements TestResultService {

    private final IOService ioService;

    private final LocalizationSettingsProvider localizationSettingsProvider;

    private final SettingsProvider settingsProvider;


    public TestResultServiceImpl(IOService ioService,
                                 LocalizationSettingsProvider localizationSettingsProvider,
                                 SettingsProvider settingsProvider) {
        this.ioService = ioService;
        this.localizationSettingsProvider = localizationSettingsProvider;
        this.settingsProvider = settingsProvider;
    }

    @Override
    public void showResult(Student student) {

        ioService.print(student.getName() + " " + student.getSurname() + ": " + student.getScore());
        if (student.getScore() >= settingsProvider.getPoints()) {
            ioService.print(localizationSettingsProvider.getTestPassMessage());
        } else {
            ioService.print(localizationSettingsProvider.getTestFailedMessage());
        }
    }
}