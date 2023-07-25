package ru.otus.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.otus.app.dao.AuthorDao;
import ru.otus.app.dao.BookDao;
import ru.otus.app.dao.GenreDao;
import ru.otus.app.dto.BookDto;
import ru.otus.app.exceptions.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Service для работы с  книгами должен ")
@SpringBootTest
@Import(BookServiceImpl.class)
public class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    private static final Long EXISTING_BOOK_ID = 1L;

    private static final String EXISTING_BOOK_NAME = "Book 1";

    private static final String EXISTING_AUTHOR_NAME = "Author 1";

    private static final String EXISTING_GENRE_NAME = "Genre 1";

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {

        BookDto expectedBook = new BookDto(
                2L, EXISTING_BOOK_NAME, EXISTING_AUTHOR_NAME, EXISTING_GENRE_NAME);

        BookDto actualBook = bookService.createBook(expectedBook);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(bookService.getBookById(expectedBook.getId()));
    }

    @DisplayName("кидать исключение при добавлении книги, если Author не существует")
    @Test
    void shouldThrowExceptionWhenAuthorNotExist() {

        BookDto bookDto = new BookDto(
                2L, EXISTING_BOOK_NAME, "Not Exist", EXISTING_GENRE_NAME);

        assertThatThrownBy(() -> bookService.createBook(bookDto))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("кидать исключение при добавлении книги, если Genre не существует")
    @Test
    void shouldThrowExceptionWhenGenreNotExist() {

        BookDto bookDto = new BookDto(
                2L, EXISTING_BOOK_NAME, EXISTING_AUTHOR_NAME, "Not Exist");


        assertThatThrownBy(() -> bookService.createBook(bookDto))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("возвращать ожидаемую книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        BookDto expectedBookDto = new BookDto(
                EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_AUTHOR_NAME, EXISTING_GENRE_NAME);

        BookDto actualBook = bookService.getBookById(expectedBookDto.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBookDto);
    }
}
