package ru.otus.app.service;

import ru.otus.app.domain.Book;

import java.util.List;

public interface BookService {

    void createBook(Book book);

    List<Book> getAllBook();

    Book getBookById(long id);

    void updateBookById(long id, String name);

    void deleteBookById(long id);

}
