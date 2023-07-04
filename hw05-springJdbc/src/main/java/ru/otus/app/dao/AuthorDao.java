package ru.otus.app.dao;

import ru.otus.app.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Author create(Author author);

    List<Author> getByName(String name);

    Optional<Author> getById(long id);


}
