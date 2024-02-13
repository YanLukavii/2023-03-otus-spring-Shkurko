package ru.otus.hw.repositories;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("BookRepositoryTest ")
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private final Genre genre_1 = new Genre("one", "Genre_1");

    private final Author author_1 = new Author("one", "Author_1");

    private final Book expectedBook = new Book("one", "Book_1", author_1, genre_1);

    private final String BOOK_TITLE_1 = "Book_1";

    private final String BOOK_TITLE_2 = "Book_2";

    private final String BOOK_TITLE_3 = "Book_3";


    @Test
    @DirtiesContext
    void shouldSetIdOnSave() {
        Mono<Book> bookMono = bookRepository.save(expectedBook);

        StepVerifier
                .create(bookMono)
                .assertNext(book -> assertNotNull(book.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldReturnAllCorrectBooks() {
        Flux<Book> bookFlux = bookRepository.findAll();

        StepVerifier
                .create(bookFlux)
                .recordWith(ArrayList::new)
                .expectNextCount(3)
                .consumeRecordedWith(results -> {
                    assertThat(results)
                            .extracting(Book::getTitle)
                            .contains(BOOK_TITLE_1, BOOK_TITLE_2, BOOK_TITLE_3);
                })
                .verifyComplete();
    }

    @Test
    void shouldFindAllBooks() {
        StepVerifier
                .create(bookRepository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

}