package ru.otus.hw.springbatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.springbatch.dto.BookDto;
import ru.otus.hw.springbatch.dto.CommentDto;
import ru.otus.hw.springbatch.mappers.BookMapper;
import ru.otus.hw.springbatch.mappers.CommentMapper;
import ru.otus.hw.springbatch.model.h2.Author;
import ru.otus.hw.springbatch.model.h2.Book;
import ru.otus.hw.springbatch.model.h2.Comment;
import ru.otus.hw.springbatch.model.h2.Genre;
import ru.otus.hw.springbatch.model.mongo.AuthorMongo;
import ru.otus.hw.springbatch.model.mongo.BookMongo;
import ru.otus.hw.springbatch.model.mongo.CommentMongo;
import ru.otus.hw.springbatch.model.mongo.GenreMongo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Service
@RequiredArgsConstructor
public class RelationshipService {

    private final BookMapper bookMapper;

    private final CommentMapper commentMapper;

    private final Map<String, Author> authorHashMap = new HashMap<>();

    private final Map<String, Genre> genreHashMap = new HashMap<>();

    private final Map<String, Book> bookHashMap = new HashMap<>();

    private final AtomicLong authorId = new AtomicLong(0);

    private final AtomicLong genreId = new AtomicLong(0);

    private final AtomicLong bookId = new AtomicLong(0);

    private final AtomicLong commentId = new AtomicLong(0);


    public Author updateAuthor(AuthorMongo authorMongo) {

        Author author = new Author(authorId.incrementAndGet(),
                authorMongo.getFullName());

        authorHashMap.put(authorMongo.getId(), author);

        return author;
    }

    public Genre updateGenre(GenreMongo genreMongo) {

        Genre genre = new Genre(genreId.incrementAndGet(),
                genreMongo.getName());

        genreHashMap.put(genreMongo.getId(), genre);

        return genre;
    }

    public BookDto updateBook(BookMongo bookMongo) {

        Book book = new Book(bookId.incrementAndGet(),
                bookMongo.getTitle(),
                authorHashMap.get(bookMongo.getAuthorMongo().getId()),
                genreHashMap.get(bookMongo.getGenreMongo().getId()));

        bookHashMap.put(bookMongo.getId(), book);

        return bookMapper.toDto(book);
    }

    public CommentDto updateComment(CommentMongo commentMongo) {

        Comment comment = new Comment(commentId.incrementAndGet(),
                commentMongo.getText(),
                bookHashMap.get(commentMongo.getBookMongo().getId()));

        return commentMapper.toDto(comment);
    }
}