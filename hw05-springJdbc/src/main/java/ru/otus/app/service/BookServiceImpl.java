package ru.otus.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.app.dao.BookDao;
import ru.otus.app.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public void createBook(Book book) {
        bookDao.insertNewBook(book);
    }

    @Override
    public List<Book> getAllBook() {
        return bookDao.getAllBooks();
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.getBookById(id);
    }

    @Override
    public void updateBookById(long id, String bookName) {
        bookDao.updateBookNameById(id, bookName);
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteBookById(id);
    }
}