package ru.otus.app.dao;

import ru.otus.app.domain.Author;

import java.util.Optional;

public interface AuthorDao {

    void insertNewAuthor(Author author);

    Optional<Author> getAuthorByName(String name);


}
