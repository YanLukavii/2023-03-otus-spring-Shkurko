package ru.otus.app.dao;

import ru.otus.app.domain.Genre;

import java.util.Optional;

public interface GenreDao {

    void insertNewGenre(Genre genre);

    Optional<Genre> getGenreByName(String genreName);

}