package ru.otus.app.service;

import org.springframework.stereotype.Component;
import ru.otus.app.dto.BookDto;

import java.util.List;

@Component
public class BookConverterImpl implements BookConverter {

    public String toStringBookDto(BookDto bookDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(bookDto.getId()).append("\n");
        sb.append("Name: ").append(bookDto.getBookName()).append("\n");
        sb.append("Author: ").append(bookDto.getAuthorName()).append("\n");
        sb.append("Genre: ").append(bookDto.getGenreName()).append("\n");
        return sb.toString();
    }

    public String toStringBookDtoList(List<BookDto> bookDtoList) {
        StringBuilder sb = new StringBuilder();
        for (BookDto bookDto : bookDtoList) {
            sb.append(this.toStringBookDto(bookDto)).append("\n");
        }
        return sb.toString();
    }
}