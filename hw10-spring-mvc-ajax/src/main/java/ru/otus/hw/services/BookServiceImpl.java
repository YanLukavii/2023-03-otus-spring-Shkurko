package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(long id) {

      Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(id)));

        return bookMapper.toDto(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {

        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDto create(BookCreateDto dto) {

        var author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(dto.getAuthorId())));

        var genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new NotFoundException("Genre with id %d not found".formatted(dto.getGenreId())));

        var book = new Book(0, dto.getTitle(), author, genre);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto update(BookUpdateDto dto) {

        var book = bookRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(dto.getId())));

        var author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(dto.getAuthorId())));

        var genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new NotFoundException("Genre with id %d not found".formatted(dto.getGenreId())));

        book.setTitle(dto.getTitle());
        book.setAuthor(author);
        book.setGenre(genre);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}