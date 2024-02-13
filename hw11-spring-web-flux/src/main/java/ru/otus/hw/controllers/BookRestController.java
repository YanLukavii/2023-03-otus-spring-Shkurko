package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookMapper bookMapper;

    @GetMapping("/api/books")
    public Flux<BookDto> getAllBooks() {
        return bookRepository
                .findAll()
                .map(bookMapper::toDto);
    }

    @PostMapping("/api/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDto> createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {

        return authorRepository.findById(bookCreateDto.getAuthorId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Author with id %s not found".formatted(bookCreateDto.getAuthorId()))
                ))
                .zipWith(genreRepository.findById(bookCreateDto.getGenreId())
                        .switchIfEmpty(Mono.error(new NotFoundException(
                                "Genre with id %s not found".formatted(bookCreateDto.getGenreId()))
                        )))
                .flatMap(tuple -> bookRepository.save(new Book(
                        bookCreateDto.getTitle(), tuple.getT1(), tuple.getT2()))
                        .map(bookMapper::toDto));

    }

    @DeleteMapping("/api/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
       return bookRepository.deleteById(id);
    }

    @PutMapping("/api/books/{id}")
    public Mono<BookDto> updateBook(@PathVariable("id") String id,
                              @Valid @RequestBody BookUpdateDto bookUpdateDto) {

        return authorRepository.findById(bookUpdateDto.getAuthorId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Author with id %s not found".formatted(bookUpdateDto.getAuthorId()))
                ))
                .zipWith(genreRepository.findById(bookUpdateDto.getGenreId())
                        .switchIfEmpty(Mono.error(new NotFoundException(
                                "Genre with id %s not found".formatted(bookUpdateDto.getGenreId()))
                        )))
                .flatMap(tuple -> bookRepository.findById(bookUpdateDto.getId())
                        .switchIfEmpty(Mono.error(new NotFoundException(
                                "Book with id %s not found".formatted(bookUpdateDto.getId()))
                        ))
                        .flatMap(book -> {
                            book.setId(bookUpdateDto.getId());
                            book.setTitle(bookUpdateDto.getTitle());
                            book.setAuthor(tuple.getT1());
                            book.setGenre(tuple.getT2());
                            return bookRepository.save(book)
                                    .map(bookMapper::toDto);
                        }));
    }
}