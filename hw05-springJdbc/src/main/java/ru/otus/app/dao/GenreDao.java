package ru.otus.app.dao;

import ru.otus.app.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    Genre create(Genre genre);

    List<Genre> getByName(String genreName);

    Optional<Genre> getById(long id);

}