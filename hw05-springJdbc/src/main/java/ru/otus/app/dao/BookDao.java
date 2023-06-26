package ru.otus.app.dao;

import ru.otus.app.domain.Book;

import java.util.List;

public interface BookDao {

    int count();

    void insertNewBook(Book book);

    Book getBookById(long id);

    List<Book> getAllBooks();

    void deleteBookById(long id);

    void updateBookNameById(long id, String bookName);

}
