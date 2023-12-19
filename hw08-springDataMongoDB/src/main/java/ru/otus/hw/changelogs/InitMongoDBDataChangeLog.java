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

    private CommentRepository commentRepository;

    @ChangeSet(order = "000", id = "dropDB", author = "stvort", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "yan", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository) {
        author_1 = authorRepository.save(new Author("Author_1"));
        author_2 = authorRepository.save(new Author("Author_2"));
        author_3 = authorRepository.save(new Author("Author_3"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "yan", runAlways = true)
    public void initGenres(GenreRepository genreRepository) {
        genre_1 = genreRepository.save(new Genre("Genre_1"));
        genre_2 = genreRepository.save(new Genre("Genre_2"));
        genre_3 = genreRepository.save(new Genre("Genre_3"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "yan", runAlways = true)
    public void initBooks(BookRepository bookRepository) {
        book_1 = bookRepository.save(new Book("Book_1", author_1, genre_1));
        book_2 = bookRepository.save(new Book("Book_2", author_2, genre_2));
        book_3 = bookRepository.save(new Book("Book_3", author_3, genre_3));
    }

    @ChangeSet(order = "004", id = "initComments", author = "yan", runAlways = true)
    public void initComments(CommentRepository commentRepository) {
          commentRepository.save(new Comment("Comment_1", book_1));
          commentRepository.save(new Comment("Comment_2", book_2));
          commentRepository.save(new Comment("Comment_3", book_3));
    }
/*
    @ChangeSet(order = "001", id = "initKnowledges", author = "stvort", runAlways = true)
    public void initKnowledges(AuthorRepository authorRepository){
        springDataKnowledge = authorRepository.save(new Knowledge("Spring Data"));
        mongockKnowledge = authorRepository.save(new Knowledge("Mongock"));
        aggregationApiKnowledge = authorRepository.save(new Knowledge("AggregationApi"));
    }

    @ChangeSet(order = "002", id = "initStudents", author = "stvort", runAlways = true)
    public void initStudents(StudentRepository repository){
        repository.save(new Student("Student #1", springDataKnowledge, mongockKnowledge));
    }

    @ChangeSet(order = "005", id = "initStudentsYan", author = "yan", runAlways = true)
    public void initStudentsYan(StudentRepository repository){
        repository.save(new Student("YAN", springDataKnowledge, mongockKnowledge));
    }

    @ChangeSet(order = "003", id = "Teacher", author = "stvort", runAlways = true)
    public void initTeachers(TeacherRepository repository){
        val teacher = new Teacher("Teacher #1", springDataKnowledge, mongockKnowledge, aggregationApiKnowledge);
        repository.save(teacher);
    }*/
}
