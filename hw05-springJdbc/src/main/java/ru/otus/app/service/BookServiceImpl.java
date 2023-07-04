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
import ru.otus.app.exceptions.NotFoundException;

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

        Book book = getBookWithExistingFieldsFromBookDto(bookDto);

        return bookDao.create(book).toDto();
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
                .orElseThrow(NotFoundException.notFoundException("Book {0} does not exist", id))
                .toDto();
    }

    @Override
    public void update(BookDto bookDto) {

        Long bookDtoId = bookDto.getId();

        bookDao.getById(bookDtoId)
                .orElseThrow(NotFoundException.notFoundException("Book {0} does not exist", bookDtoId));

        Book book = getBookWithExistingFieldsFromBookDto(bookDto);
        book.setId(bookDtoId);
        bookDao.update(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookDao.deleteById(id);
    }

    private Book getBookWithExistingFieldsFromBookDto (BookDto bookDto) {

        String authorName = bookDto.getAuthorName();
        List<Author> authors = authorDao.getByName(authorName);
        if (authors.isEmpty()) {
            throw new NotFoundException("Author {0} does not exist", authorName);
        }
        Author author = authors.get(0);

        String genreName = bookDto.getGenreName();
        List<Genre> genres = genreDao.getByName(genreName);
        if (genres.isEmpty()) {
            throw new NotFoundException("Genre {0} does not exist", genreName);
        }
        Genre genre = genres.get(0);

        return new Book(bookDto.getBookName(), author, genre);
    }
}