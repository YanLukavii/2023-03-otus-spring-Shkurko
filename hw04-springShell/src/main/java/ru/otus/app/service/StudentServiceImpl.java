package ru.otus.app.service;

import org.springframework.stereotype.Service;
import ru.otus.app.domain.Student;
import ru.otus.app.repository.LocalizationAppPropertiesProvider;

@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    private final LocalizationAppPropertiesProvider localizationAppPropertiesProvider;

    public StudentServiceImpl(IOService ioService,
                              LocalizationAppPropertiesProvider localizationAppPropertiesProvider) {
        this.ioService = ioService;
        this.localizationAppPropertiesProvider = localizationAppPropertiesProvider;
    }

    @Override
    public Student createStudent() {

        ioService.print(localizationAppPropertiesProvider.getPropertyValue("student.name"));
        String name = ioService.read();
        ioService.print(localizationAppPropertiesProvider.getPropertyValue("student.surname"));
        String surname = ioService.read();
        return new Student(name, surname);
    }

}