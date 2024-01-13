package ru.otus.hw.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author author_1;

    private Author author_2;

    private Author author_3;

    private Genre genre_1;

    private Genre genre_2;

    private Genre genre_3;

    private Book book_1;

    private Book book_2;

    private Book book_3;

    @ChangeSet(order = "000", id = "dropDB", author = "yan", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "yan", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository) {
        author_1 = authorRepository.save(new Author("one","Author_1"));
        author_2 = authorRepository.save(new Author("two","Author_2"));
        author_3 = authorRepository.save(new Author("three","Author_3"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "yan", runAlways = true)
    public void initGenres(GenreRepository genreRepository) {
        genre_1 = genreRepository.save(new Genre("one","Genre_1"));
        genre_2 = genreRepository.save(new Genre("two","Genre_2"));
        genre_3 = genreRepository.save(new Genre("three","Genre_3"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "yan", runAlways = true)
    public void initBooks(BookRepository bookRepository) {
        book_1 = bookRepository.save(new Book("one","Book_1", author_1, genre_1));
        book_2 = bookRepository.save(new Book("two","Book_2", author_2, genre_2));
        book_3 = bookRepository.save(new Book("three","Book_3", author_3, genre_3));
    }

    @ChangeSet(order = "004", id = "initComments", author = "yan", runAlways = true)
    public void initComments(CommentRepository commentRepository) {
          commentRepository.save(new Comment("one","Comment_1", book_1));
          commentRepository.save(new Comment("two","Comment_2", book_2));
          commentRepository.save(new Comment("three","Comment_3", book_3));
    }
}
