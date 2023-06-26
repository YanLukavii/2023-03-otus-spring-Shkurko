package ru.otus.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.app.domain.Author;
import ru.otus.app.domain.Book;
import ru.otus.app.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Override
    public int count() {
        Integer count = namedParameterJdbcOperations
                .getJdbcOperations()
                .queryForObject("select count(*) from book", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insertNewBook(Book book) {

        String authorName = book.getAuthor().getName();
        String genreName = book.getGenre().getName();

        if (authorDao.getAuthorByName(authorName).isEmpty()) {
            authorDao.insertNewAuthor(new Author(authorName));
        }
        if (genreDao.getGenreByName(genreName).isEmpty()) {
            genreDao.insertNewGenre(new Genre(genreName));
        }

        namedParameterJdbcOperations
                .update("insert into book (name, author_id, genre_id) values (:name, :author_id, :genre_id)",
                        Map.of("name", book.getName(),
                                "author_id", authorDao.getAuthorByName(authorName).orElseThrow().getId(),
                                "genre_id", genreDao.getGenreByName(genreName).orElseThrow().getId()));
    }

    @Override
    public Book getBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "SELECT Book.id as book_id, Book.name as book_name," +
                        " Author.id as author_id, Author.name as author_name," +
                        " Genre.id as genre_id, Genre.name genre_name" +
                        " FROM Book" +
                        " JOIN Author ON Book.author_id = Author.id" +
                        " JOIN Genre ON Book.genre_id = Genre.id" +
                        " WHERE Book.id = :id;", params, new BookRowMapper()
        );
    }

    @Override
    public List<Book> getAllBooks() {
        return namedParameterJdbcOperations
                .getJdbcOperations()
                .query("SELECT Book.id as book_id, Book.name as book_name," +
                        " Author.id as author_id, Author.name as author_name," +
                        " Genre.id as genre_id, Genre.name genre_name" +
                        " FROM Book" +
                        " JOIN Author ON Book.author_id = Author.id" +
                        " JOIN Genre ON Book.genre_id = Genre.id;", new BookRowMapper());
    }

    @Override
    public void deleteBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from book where id = :id", params
        );
    }

    @Override
    public void updateBookNameById(long id, String bookName) {
        namedParameterJdbcOperations.update(
                "UPDATE Book SET NAME = :name WHERE ID = :id;",
                Map.of("id", id,
                        "name", bookName));

    }


    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

            long id = rs.getLong("book_id");
            String name = rs.getString("book_name");
            long authorId = rs.getLong("author_id");
            String authorName = rs.getString("author_name");
            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");

            return new Book(id, name, new Author(authorId, authorName), new Genre(genreId, genreName));
        }
    }
}
