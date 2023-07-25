package ru.otus.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.app.domain.Author;
import ru.otus.app.domain.Book;
import ru.otus.app.domain.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        Integer count = namedParameterJdbcOperations
                .getJdbcOperations()
                .queryForObject("select count(*) from book", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public Book create(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into book (name, author_id, genre_id) values (?, ?, ?)";

        namedParameterJdbcOperations.getJdbcOperations().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getName());
            ps.setLong(2, book.getAuthor().getId());
            ps.setLong(3, book.getGenre().getId());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        book.setId(generatedId);

        return book;
    }

    @Override
    public Optional<Book> getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "SELECT Book.id as book_id, Book.name as book_name," +
                        " Author.id as author_id, Author.name as author_name," +
                        " Genre.id as genre_id, Genre.name genre_name" +
                        " FROM Book" +
                        " JOIN Author ON Book.author_id = Author.id" +
                        " JOIN Genre ON Book.genre_id = Genre.id" +
                        " WHERE Book.id = :id;", params, new OptionalBookRowMapper()
        );
    }

    @Override
    public List<Book> getAll() {
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
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from book where id = :id", params
        );
    }

    @Override
    public void update(Book book) {
        namedParameterJdbcOperations.update(
                "UPDATE Book SET NAME = :name, author_id = :author_id, genre_id = :genre_id WHERE ID = :id;",
                Map.of("id", book.getId(),
                        "name", book.getName(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()));
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

    private static class OptionalBookRowMapper implements RowMapper<Optional<Book>> {

        @Override
        public Optional<Book> mapRow(ResultSet rs, int rowNum) throws SQLException {

            long id = rs.getLong("book_id");
            String name = rs.getString("book_name");
            long authorId = rs.getLong("author_id");
            String authorName = rs.getString("author_name");
            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");

            return Optional.of(new Book(id, name, new Author(authorId, authorName), new Genre(genreId, genreName)));
        }
    }
}
