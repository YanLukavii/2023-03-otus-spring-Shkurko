package ru.otus.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.app.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с  авторами должно ")
@JdbcTest
@Import(AuthorDaoImpl.class)
public class AuthorDaoImplTest {
    private static final long EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_NAME = "Author 1";
    @Autowired
    private AuthorDaoImpl authorDao;

    @DisplayName("возвращать ожидаемого автора по его имени")
    @Test
    void shouldReturnExpectedAuthorByName() {

        Optional<Author> expectedAuthor = Optional.of(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME));
        Optional<Author> actualAuthor = authorDao.getAuthorByName(expectedAuthor.orElseThrow().getName());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {

        Optional<Author> expectedAuthor = Optional.of(new Author(2,
                "Author 2"));
        authorDao.insertNewAuthor(expectedAuthor.get());

        Optional<Author> actualAuthor = authorDao.getAuthorByName("Author 2");
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}