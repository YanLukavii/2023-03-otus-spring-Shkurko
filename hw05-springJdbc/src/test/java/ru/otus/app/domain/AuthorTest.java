package ru.otus.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Author")
class AuthorTest {
    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Author author = new Author(1L, "Author 1");
        assertEquals("Author 1", author.getName());
        assertEquals(1L, author.getId());

        Author author2 = new Author("Author 2");
        assertEquals("Author 2", author2.getName());
    }
}
