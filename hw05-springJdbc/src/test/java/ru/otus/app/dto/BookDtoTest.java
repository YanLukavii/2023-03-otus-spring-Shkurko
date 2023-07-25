package ru.otus.app.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.app.domain.Author;
import ru.otus.app.domain.Book;
import ru.otus.app.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс BookDto")
public class BookDtoTest {

    private static final Long BOOK_ID = 1L;

    private static final String BOOK_NAME = "Book 1";

    private static final String AUTHOR_NAME = "Author 1";

    private static final String GENRE_NAME = "Genre 1";

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {

        BookDto bookDto = new BookDto(BOOK_ID, BOOK_NAME, AUTHOR_NAME, GENRE_NAME);

        assertEquals(BOOK_NAME, bookDto.getBookName());
        assertEquals(BOOK_ID, bookDto.getId());
        assertEquals(AUTHOR_NAME, bookDto.getAuthorName());
        assertEquals(GENRE_NAME, bookDto.getGenreName());

    }

    @DisplayName("корректно конвертируется в Book")
    @Test
    void shouldHaveCorrectCovertToBook() {

        BookDto bookDto = new BookDto(BOOK_ID, BOOK_NAME, AUTHOR_NAME, GENRE_NAME);

        Book expectedBookDto = new Book(
                bookDto.getId(), bookDto.getBookName(), new Author(AUTHOR_NAME), new Genre(GENRE_NAME));

        Book actualBookDto = bookDto.toBook();

        assertThat(actualBookDto).usingRecursiveComparison().isEqualTo(expectedBookDto);

    }


}
