package ru.otus.app.service;

import ru.otus.app.dto.BookDto;

import java.util.List;

public interface BookConverter {

    String toStringBookDto(BookDto bookDto);

    String toStringBookDtoList(List<BookDto> bookDtoList);
}
