package ru.otus.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.app.dao.AuthorDao;
import ru.otus.app.dao.BookDao;
import ru.otus.app.dao.GenreDao;
import ru.otus.app.domain.Author;
import ru.otus.app.domain.Book;
import ru.otus.app.domain.Genre;
import ru.otus.app.dto.BookDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Override
    public BookDto createBook(BookDto bookDto) {

        List<Author> authors = authorDao.getByName(bookDto.getAuthorName());
        if (authors.isEmpty()) {
            throw new IllegalArgumentException("Author does not exist");
        }
        Author author = authors.get(0);

        List<Genre> genres = genreDao.getByName(bookDto.getGenreName());
        if (genres.isEmpty()) {
            throw new IllegalArgumentException("Genre does not exist");
        }
        Genre genre = genres.get(0);

        return bookDao.create(new Book(bookDto.getBookName(), author, genre))
                .toDto();
    }

    @Override
    public List<BookDto> getAllBook() {
        List<Book> books = bookDao.getAll();
        return books.stream()
                .map(Book::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookDao.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book does not exist"))
                .toDto();
    }

    @Override
    public void update(BookDto bookDto) {

        bookDao.getById(bookDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Book does not exist"));

        List<Author> authors = authorDao.getByName(bookDto.getAuthorName());
        if (authors.isEmpty()) {
            throw new IllegalArgumentException("Author does not exist");
        }
        Author author = authors.get(0);

        List<Genre> genres = genreDao.getByName(bookDto.getGenreName());
        if (genres.isEmpty()) {
            throw new IllegalArgumentException("Genre does not exist");
        }
        Genre genre = genres.get(0);

        Book book = bookDto.toBook();
        book.setAuthor(author);
        book.setGenre(genre);

        bookDao.update(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookDao.deleteById(id);
    }
}