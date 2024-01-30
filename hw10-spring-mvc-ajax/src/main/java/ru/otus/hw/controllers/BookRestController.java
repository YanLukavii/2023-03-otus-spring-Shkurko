package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @PostMapping("/api/books")
    public BookDto createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        return bookService.create(bookCreateDto);
    }

    @GetMapping("/api/authors")
    public List<AuthorDto> getAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/api/genres")
    public List<GenreDto> getGenres() {
        return genreService.findAll();
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable("id") long id) {
         bookService.deleteById(id);
    }

    @PutMapping("/api/books/{id}")
    public BookDto updateBook(@PathVariable("id") long id,
                              @Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.update(bookUpdateDto);
    }
}