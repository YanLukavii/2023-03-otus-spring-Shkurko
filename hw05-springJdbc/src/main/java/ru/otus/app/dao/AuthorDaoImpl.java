package ru.otus.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.app.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;


    @Override
    public void insertNewAuthor(Author author) {
        namedParameterJdbcOperations.update("insert into Author (name) values (:name)",
                Map.of("name", author.getName()));
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        try {
            Map<String, Object> params = Collections.singletonMap("name", name);
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                    "select id, name from Author where name = :name", params, new AuthorMapper()
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Author> getAuthorById(long id) {
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            return namedParameterJdbcOperations.queryForObject(
                    "select id, name from author where id = :id", params, new OptionalAuthorMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Author(id, name);
        }
    }

    private static class OptionalAuthorMapper implements RowMapper<Optional<Author>> {

        @Override
        public Optional<Author> mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return Optional.of(new Author(id, name));
        }
    }

}
