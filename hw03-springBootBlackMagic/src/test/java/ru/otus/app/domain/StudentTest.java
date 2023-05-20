package ru.otus.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.app.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Student")
public class StudentTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Student student = new Student("Vasyl", "Vasylev");
        assertEquals("Vasyl", student.getName());
        assertEquals("Vasylev", student.getSurname());
        assertEquals(0, student.getScore());
    }
}