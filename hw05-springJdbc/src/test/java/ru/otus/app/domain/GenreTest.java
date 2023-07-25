package ru.otus.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Genre")
class GenreTest {
    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Genre genre = new Genre(1L, "Genre 1");
        assertEquals("Genre 1", genre.getName());
        assertEquals(1, genre.getId());

        Genre genre2 = new Genre("Genre 2");
        assertEquals("Genre 2", genre2.getName());
    }
}