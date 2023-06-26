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
        Map<String, Object> params = Collections.singletonMap("name", name);

        try {
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                    "select id, name from Author where name = :name", params, new AuthorMapper()
            ));
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

}
