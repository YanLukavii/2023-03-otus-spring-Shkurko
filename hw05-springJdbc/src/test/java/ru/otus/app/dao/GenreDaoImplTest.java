package ru.otus.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.app.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с  жанрамм должно ")
@JdbcTest
@Import(GenreDaoImpl.class)
public class GenreDaoImplTest {

    private static final long EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Genre 1";
    @Autowired
    private GenreDaoImpl genreDao;

    @DisplayName("возвращать ожидаемый жанр по его имени")
    @Test
    void shouldReturnExpectedGenreByName() {

        Optional<Genre> expectedGenre = Optional.of(new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME));
        Optional<Genre> actualGenre = genreDao.getGenreByName(expectedGenre.orElseThrow().getName());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {

        Optional<Genre> expectedGenre = Optional.of(new Genre(2,
                "Genre 2"));
        genreDao.insertNewGenre(expectedGenre.get());

        Optional<Genre> actualGenre = genreDao.getGenreByName("Genre 2");
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }


}