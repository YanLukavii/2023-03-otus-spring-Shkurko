package ru.otus.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.app.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с  жанрамм должно ")
@JdbcTest
@Import(GenreDaoImpl.class)
public class GenreDaoImplTest {

    private static final Long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_GENRE_NAME = "Genre 1";
    @Autowired
    private GenreDaoImpl genreDao;

    @DisplayName("возвращать ожидаемый жанр по его имени")
    @Test
    void shouldReturnExpectedGenreByName() {

        List<Genre> expectedGenre = List.of(new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME));
        List<Genre> actualGenre = genreDao.getByName(EXISTING_GENRE_NAME);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {

        List<Genre> expectedGenre = List.of(new Genre(2L,
                "Genre 2"));
        genreDao.create(expectedGenre.get(0));

        List<Genre> actualGenre = genreDao.getByName("Genre 2");
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("возвращать ожидаемый жанр по его Id")
    @Test
    void shouldReturnExpectedGenreById() {

        Optional<Genre> expectedGenre = Optional.of(new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME));
        Optional<Genre> actualGenre = genreDao.getById(expectedGenre.orElseThrow().getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}