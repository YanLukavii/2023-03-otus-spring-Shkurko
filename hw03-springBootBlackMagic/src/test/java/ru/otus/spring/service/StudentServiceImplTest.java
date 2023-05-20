package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.app.repository.LocalizationAppPropertiesProvider;
import ru.otus.app.service.IOService;
import ru.otus.app.service.StudentServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("класс StudentServiceImpl")
public class StudentServiceImplTest {

    IOService ioService = Mockito.spy(IOService.class);

    LocalizationAppPropertiesProvider localizationAppPropertiesProvider = Mockito.mock(LocalizationAppPropertiesProvider.class);

    String name = "Vasyl";

    String surname = "Vasylev";

    @DisplayName("корректно создает студента")
    @Test
    void shouldReturnCorrectStudent() {

        Mockito.when(ioService.read())
                .thenReturn(name)
                .thenReturn(surname);

        var studentServiceImpl = new StudentServiceImpl(ioService, localizationAppPropertiesProvider);
        var student = studentServiceImpl.createStudent();

        assertEquals(name, student.getName());
        assertEquals(surname, student.getSurname());

    }
}
