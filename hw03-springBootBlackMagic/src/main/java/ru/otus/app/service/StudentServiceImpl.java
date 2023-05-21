package ru.otus.app.service;

import org.springframework.stereotype.Service;
import ru.otus.app.domain.Student;
import ru.otus.app.configs.providers.LocalizationSettingsProvider;

@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    private final LocalizationSettingsProvider localizationSettingsProvider;

    public StudentServiceImpl(IOService ioService, LocalizationSettingsProvider localizationSettingsProvider) {
        this.ioService = ioService;
        this.localizationSettingsProvider = localizationSettingsProvider;
    }

    @Override
    public Student createStudent() {

        ioService.print(localizationSettingsProvider.getStudentNameGreetingMessage());
        String name = ioService.read();
        ioService.print(localizationSettingsProvider.getStudentSurnameGreetingMessage());
        String surname = ioService.read();
        return new Student(name, surname);
    }

}