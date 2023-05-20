package ru.otus.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.app.domain.Student;
import ru.otus.app.repository.LocalizationAppPropertiesProvider;

@Service
public class TestResultServiceImpl implements TestResultService {

    private final IOService ioService;

    private final int passedPoints;

    private final LocalizationAppPropertiesProvider localizationAppPropertiesProvider;


    public TestResultServiceImpl(IOService ioService,
                                 @Value("${application.points}") int passedPoints,
                                 LocalizationAppPropertiesProvider localizationAppPropertiesProvider) {
        this.ioService = ioService;
        this.passedPoints = passedPoints;
        this.localizationAppPropertiesProvider = localizationAppPropertiesProvider;
    }

    @Override
    public void showResult(Student student) {

        ioService.print(student.getName() + " " + student.getSurname() + ": " + student.getScore());
        if (student.getScore() >= passedPoints) {
            ioService.print(localizationAppPropertiesProvider.getPropertyValue("test.passed"));
        } else {
            ioService.print(localizationAppPropertiesProvider.getPropertyValue("test.failed"));
        }
    }
}