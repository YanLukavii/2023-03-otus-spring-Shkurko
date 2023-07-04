package ru.otus.app.dao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.app.domain.Author;
import ru.otus.app.domain.Book;
import ru.otus.app.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с  книгами должно ")
@JdbcTest
@Import(BookDaoImpl.class)
class BookDaoImplTest {

    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_NAME = "Book 1";
    private static final String EXISTING_AUTHOR_NAME = "Author 1";
    private static final String EXISTING_GENRE_NAME = "Genre 1";

    @Autowired
    private BookDaoImpl bookDao;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @DisplayName("возвращать ожидаемое количество книг из БД")
    @Test
    void shouldReturnExpectedBookCount() {
        int actualPersonsCount = bookDao.count();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {

        Book expectedBook = new Book(2L, EXISTING_BOOK_NAME,
                new Author(1L, EXISTING_AUTHOR_NAME),
                new Genre(1L, EXISTING_GENRE_NAME));
        bookDao.create(expectedBook);

        Book actualBook = bookDao.getById(2).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемую книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME,
                new Author(1L, EXISTING_AUTHOR_NAME),
                new Genre(1L, EXISTING_GENRE_NAME));
        Book actualBook = bookDao.getById(expectedBook.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("удалять заданную книгу по её id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME
                , new Author(1L, EXISTING_AUTHOR_NAME)
                , new Genre(1L, EXISTING_GENRE_NAME));

        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList)
                .containsExactlyInAnyOrder(expectedBook);
    }
}
