package ru.otus.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Book")
public class BookTest {

    private final Author author = new Author("Author 1");

    private final Genre genre = new Genre("Genre 1");

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Book book = new Book(1, "Book 1", author, genre);
        assertEquals("Book 1", book.getName());
        assertEquals(1, book.getId());
        assertEquals(author, book.getAuthor());
        assertEquals(genre, book.getGenre());

        Book book2 = new Book("Book 1", author, genre);
        assertEquals("Book 1", book2.getName());
        assertEquals(author, book2.getAuthor());
        assertEquals(genre, book2.getGenre());

    }
}
