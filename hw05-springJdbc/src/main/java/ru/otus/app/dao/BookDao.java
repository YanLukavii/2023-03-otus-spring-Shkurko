package ru.otus.app.dao;

import ru.otus.app.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    int count();

    Book create(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);

    void update(Book book);

}
