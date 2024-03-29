package ru.otus.hw.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;

import java.util.List;

@FeignClient(name = "library-app")
public interface LibraryServiceProxy {

    @CircuitBreaker(name = "library-app", fallbackMethod = "fallbackGetAllBooks")
    @GetMapping("/api/books")
    List<BookDto> getAllBooks();

    default List<BookDto> fallbackGetAllBooks(Throwable throwable) {
        return List.of(new BookDto(626L,"Default title","Default author","Default genre"));
    }

    @PostMapping("/api/books")
    @ResponseStatus(HttpStatus.CREATED)
    BookDto createBook(@Valid @RequestBody BookCreateDto bookCreateDto);

    @DeleteMapping("/api/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBook(@PathVariable("id") long id);

    @PutMapping("/api/books/{id}")
    BookDto updateBook(@PathVariable("id") long id,
                      @Valid @RequestBody BookUpdateDto bookUpdateDto);

}