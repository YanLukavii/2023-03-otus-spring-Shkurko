package ru.otus.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.app.repository.LocalizationAppPropertiesProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("класс StudentServiceImpl")
public class StudentServiceImplTest {

    @MockBean
    private IOService ioService;

    @MockBean
    private LocalizationAppPropertiesProvider localizationAppPropertiesProvider;

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
