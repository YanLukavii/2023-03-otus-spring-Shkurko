package ru.otus.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.app.dto.BookDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Long id;

    private String name;

    private Author author;

    private Genre genre;

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public BookDto toDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(this.id);
        bookDto.setBookName(this.name);
        bookDto.setAuthorName(this.author.getName());
        bookDto.setGenreName(this.genre.getName());
        return bookDto;
    }

}
