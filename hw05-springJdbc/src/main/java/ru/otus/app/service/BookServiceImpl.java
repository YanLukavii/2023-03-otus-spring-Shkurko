package ru.otus.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.app.dao.AuthorDao;
import ru.otus.app.dao.BookDao;
import ru.otus.app.dao.GenreDao;
import ru.otus.app.domain.Author;
import ru.otus.app.domain.Book;
import ru.otus.app.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Override
    public void createBook(Book book) {

        String authorName = book.getAuthor().getName();
        String genreName = book.getGenre().getName();

        if (authorDao.getAuthorByName(authorName).isEmpty()) {
            throw new RuntimeException("Такого автора не существет");
        }
        if (genreDao.getGenreByName(genreName).isEmpty()) {
            throw new RuntimeException("Такого жанра не существет");
        } else {
            long idAuthor = authorDao.getAuthorByName(authorName).orElseThrow().getId();
            long idGenre = genreDao.getGenreByName(genreName).orElseThrow().getId();

            Book bookForInsertWithIdForAuthorAndGenre = new Book(book.getName()
                    , new Author(idAuthor, authorName), new Genre(idGenre, genreName));

            bookDao.insertNewBook(bookForInsertWithIdForAuthorAndGenre);
        }
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