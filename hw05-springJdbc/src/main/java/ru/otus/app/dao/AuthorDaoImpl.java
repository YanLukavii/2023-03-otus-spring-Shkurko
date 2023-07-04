package ru.otus.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.app.domain.Author;

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
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;


    @Override
    public Author create(Author author) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into Author (name) values (?)";
        namedParameterJdbcOperations.getJdbcOperations().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, author.getName());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        author.setId(generatedId);

        return author;
    }

    @Override
    public List<Author> getByName(String name) {

        Map<String, Object> params = Collections.singletonMap("name", name);
        return namedParameterJdbcOperations.query(
                "select id, name from Author where name = :name", params, new AuthorMapper()
        );
    }

    @Override
    public Optional<Author> getById(long id) {
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
