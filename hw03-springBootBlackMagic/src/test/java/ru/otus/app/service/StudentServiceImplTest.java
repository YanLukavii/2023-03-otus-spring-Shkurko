package ru.otus.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.app.configs.LocalizationAppProps;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("класс StudentServiceImpl")
public class StudentServiceImplTest {

    IOService ioService = Mockito.spy(IOService.class);

    LocalizationAppProps localizationAppProps = Mockito.mock(LocalizationAppProps.class);

    String name = "Vasyl";

    String surname = "Vasylev";

    @DisplayName("корректно создает студента")
    @Test
    void shouldReturnCorrectStudent() {

        Mockito.when(ioService.read())
                .thenReturn(name)
                .thenReturn(surname);

        var studentServiceImpl = new StudentServiceImpl(ioService, localizationAppProps);
        var student = studentServiceImpl.createStudent();

        assertEquals(name, student.getName());
        assertEquals(surname, student.getSurname());

    }
}
