package ru.otus.app.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.app.domain.Author;
import ru.otus.app.domain.Book;
import ru.otus.app.domain.Genre;
import ru.otus.app.service.BookService;
import ru.otus.app.service.IOService;

import java.util.List;


@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final IOService ioService;

    private final BookService bookService;

    @ShellMethod(value = "Get All Books", key = {"all","a"})
    public void getAllBook() {

        List<Book> bookList = bookService.getAllBook();

        for (Book book : bookList
        ) {
            ioService.print("ID:" + book.getId()
                    + " Название:" + book.getName()
                    + " Автор:" + book.getAuthor().getName()
                    + " Жанр:" + book.getGenre().getName());
        }
    }

    @ShellMethod(value = "new book", key = {"new","n"})
    public void createNewBook() {
        ioService.print("Введите название книги");
        String bookName = ioService.read();

        ioService.print("Введите автора книги");
        String authorName = ioService.read();

        ioService.print("Введите жанр книги");
        String ganreName = ioService.read();

        bookService.createBook(new Book(bookName, new Author(authorName), new Genre(ganreName)));

        ioService.print("Книга создана");
    }

    @ShellMethod(value = "get book by id", key = {"book by id","b","g"})
    public void getBookById() {

        ioService.print("Введите id книги");
        String idAsString = ioService.read();
        long id = Long.parseLong(idAsString);
        Book book = bookService.getBookById(id);

        ioService.print("ID:" + book.getId()
                + " Название:" + book.getName()
                + " Автор:" + book.getAuthor().getName()
                + " Жанр:" + book.getGenre().getName());
    }

    @ShellMethod(value = "update name book by ID", key = {"update","u"})
    public void updateBookNameById() {

        ioService.print("Введите id книги");
        String idAsString = ioService.read();
        long id = Long.parseLong(idAsString);

        ioService.print("Введите новое название книги");
        String bookName = ioService.read();

        bookService.updateBookById(id, bookName);
        ioService.print("Книга изменена!");

    }

    @ShellMethod(value = "delete book by ID", key = {"delete","d"})
    public void deleteBookById() {

        ioService.print("Введите id книги");
        String idAsString = ioService.read();
        long id = Long.parseLong(idAsString);

        bookService.deleteBookById(id);
        ioService.print("Книга удалена!");
    }
}
