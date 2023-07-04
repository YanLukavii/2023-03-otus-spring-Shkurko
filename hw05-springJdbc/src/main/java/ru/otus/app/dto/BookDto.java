package ru.otus.app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.app.domain.Author;
import ru.otus.app.domain.Book;
import ru.otus.app.domain.Genre;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {

    private Long id;

    private String bookName;

    private String authorName;

    private String genreName;

    public Book toBook() {
        Book book = new Book();
        book.setId(this.id);
        book.setName(this.bookName);
        book.setAuthor(new Author(this.authorName));
        book.setGenre(new Genre(this.genreName));
        return book;
    }
}