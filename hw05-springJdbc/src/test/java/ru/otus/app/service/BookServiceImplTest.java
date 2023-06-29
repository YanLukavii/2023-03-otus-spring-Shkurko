package ru.otus.app.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.otus.app.dao.AuthorDao;
import ru.otus.app.dao.BookDao;
import ru.otus.app.dao.GenreDao;
import ru.otus.app.domain.Author;
import ru.otus.app.domain.Book;
import ru.otus.app.domain.Genre;

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

    private static final int EXISTING_BOOK_ID = 1;

    private static final String EXISTING_BOOK_NAME = "Book 1";

    private static final String EXISTING_AUTHOR_NAME = "Author 1";

    private static final String EXISTING_GENRE_NAME = "Genre 1";

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {

        Book expectedBook = new Book(2, EXISTING_BOOK_NAME,
                new Author(1, EXISTING_AUTHOR_NAME),
                new Genre(1, EXISTING_GENRE_NAME));
        bookService.createBook(expectedBook);

        Book actualBook = bookService.getBookById(2);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("кидать исключение при добавлении книги, если Author не существует")
    @Test
    void shouldThrowExceptionWhenAuthorNotExist() {

        Book book = new Book(2, EXISTING_BOOK_NAME,
                new Author(1, "Not Exist"),
                new Genre(1, EXISTING_GENRE_NAME));

        assertThatThrownBy(() -> bookService.createBook(book))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("кидать исключение при добавлении книги, если Genre не существует")
    @Test
    void shouldThrowExceptionWhenGenreNotExist() {

        Book book = new Book(2, EXISTING_BOOK_NAME,
                new Author(1, EXISTING_AUTHOR_NAME),
                new Genre(1, "Not Exist"));

        assertThatThrownBy(() -> bookService.createBook(book))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("возвращать ожидаемую книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME,
                new Author(1, EXISTING_AUTHOR_NAME),
                new Genre(1, EXISTING_GENRE_NAME));
        Book actualBook = bookService.getBookById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }
}