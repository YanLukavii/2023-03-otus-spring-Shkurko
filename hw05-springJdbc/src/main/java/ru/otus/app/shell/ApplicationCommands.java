package ru.otus.app.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.app.dto.BookDto;
import ru.otus.app.service.BookConverter;
import ru.otus.app.service.BookService;
import ru.otus.app.service.OutputService;

import java.util.List;


@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final BookConverter bookConverter;

    private final BookService bookService;

    private final OutputService outPutService;

    @ShellMethod(value = "Get All Books", key = {"all", "a"})
    public void getAllBook() {
        List<BookDto> bookDtoList = bookService.getAllBook();
        outPutService.print(bookConverter.toStringBookDtoList(bookDtoList));
    }

    @ShellMethod(value = "Create new book", key = {"new", "n"})
    public void createNewBook(
            @ShellOption(help = "Book name") String bookName,
            @ShellOption(help = "Author name") String authorName,
            @ShellOption(help = "Genre name") String genreName
    ) {
        BookDto bookDto = new BookDto();
        bookDto.setBookName(bookName);
        bookDto.setAuthorName(authorName);
        bookDto.setGenreName(genreName);

        BookDto newBook = bookService.createBook(bookDto);

        outPutService.print("Book successfully created");
        outPutService.print(bookConverter.toStringBookDto(newBook));

    }

    @ShellMethod(value = "Get book by Id", key = {"book by id", "b", "g"})
    public void getBookById(
            @ShellOption(help = "Book Id") Long bookId
    ) {
        BookDto bookDto = bookService.getBookById(bookId);
        outPutService.print(bookConverter.toStringBookDto(bookDto));
    }

    @ShellMethod(value = "Update book", key = {"update", "u"})
    public void updateBook(
            @ShellOption(help = "Book Id") Long bookId,
            @ShellOption(help = "Book name") String bookName,
            @ShellOption(help = "Author name") String authorName,
            @ShellOption(help = "Genre name") String genreName
    ) {
        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);
        bookDto.setBookName(bookName);
        bookDto.setAuthorName(authorName);
        bookDto.setGenreName(genreName);

        bookService.update(bookDto);
        outPutService.print("Book successfully updated!");
    }

    @ShellMethod(value = "Delete book by Id", key = {"delete", "d"})
    public void deleteBookById(
            @ShellOption(help = "Book Id") Long bookId
    ) {
        bookService.deleteBookById(bookId);
        outPutService.print("Book successfully deleted!");
    }
}
