package ru.otus.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.app.domain.Author;

import java.util.List;
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

        List<Author> expectedAuthors = List.of(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME));
        List<Author> actualAuthors = authorDao.getByName(EXISTING_AUTHOR_NAME);
        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {

        List<Author> expectedAuthor = List.of(new Author(2L, "Author 2"));
        authorDao.create(expectedAuthor.get(0));

        List<Author> actualAuthor = authorDao.getByName("Author 2");
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("возвращать ожидаемого автора по его Id")
    @Test
    void shouldReturnExpectedAuthorById() {

        Optional<Author> expectedAuthor = Optional.of(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME));
        Optional<Author> actualAuthor = authorDao.getById(expectedAuthor.orElseThrow().getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}