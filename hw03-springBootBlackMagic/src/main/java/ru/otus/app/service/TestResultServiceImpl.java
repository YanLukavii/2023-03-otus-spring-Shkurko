package ru.otus.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.app.configs.providers.SettingsProvider;
import ru.otus.app.domain.Student;
import ru.otus.app.repository.LocalizationAppPropertiesProvider;

@Service
public class TestResultServiceImpl implements TestResultService {

    private final IOService ioService;

    private final LocalizationAppPropertiesProvider localizationAppPropertiesProvider;

    private final SettingsProvider settingsProvider;

    @Autowired
    public TestResultServiceImpl(IOService ioService,
                                 LocalizationAppPropertiesProvider localizationAppPropertiesProvider,
                                 SettingsProvider settingsProvider) {
        this.ioService = ioService;
        this.localizationAppPropertiesProvider = localizationAppPropertiesProvider;
        this.settingsProvider = settingsProvider;
    }

    @Override
    public void showResult(Student student) {

        ioService.print(student.getName() + " " + student.getSurname() + ": " + student.getScore());
        if (student.getScore() >= settingsProvider.getPoints()) {
            ioService.print(localizationAppPropertiesProvider.getPropertyValue("test.passed"));
        } else {
            ioService.print(localizationAppPropertiesProvider.getPropertyValue("test.failed"));
        }
    }
}