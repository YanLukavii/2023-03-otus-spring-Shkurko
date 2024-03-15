package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.feign.LibraryServiceProxy;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final LibraryServiceProxy libraryServiceProxy;

    @Override
    public List<BookDto> findAllBooks() {
        return libraryServiceProxy.getAllBooks();
    }

    @Override
    public BookDto create(BookCreateDto bookCreateDto) {
        return libraryServiceProxy.createBook(bookCreateDto);
    }

    @Override
    public void deleteById(long id) {
        libraryServiceProxy.deleteBook(id);
    }

    @Override
    public BookDto update(long id, BookUpdateDto bookUpdateDto) {
        bookUpdateDto.setId(id);
        return libraryServiceProxy.updateBook(id, bookUpdateDto);
    }
}
