package ru.otus.hw.services;

import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;

import java.util.List;

public interface BookService {

    List<BookDto> findAllBooks();

    BookDto create(BookCreateDto bookCreateDto);

    void deleteById(long id);

    BookDto update(long id, BookUpdateDto bookUpdateDto);


}
