package ru.otus.app.service;

import ru.otus.app.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto book);

    List<BookDto> getAllBook();

    BookDto getBookById(Long id);

    void update(BookDto book);

    void deleteBookById(Long id);

}
