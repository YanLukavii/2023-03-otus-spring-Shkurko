package ru.otus.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
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
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Genre create(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into Genre (name) values (?)";
        namedParameterJdbcOperations.getJdbcOperations().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, genre.getName());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        genre.setId(generatedId);

        return genre;
    }

    @Override
    public List<Genre> getByName(String genreName) {
        Map<String, Object> params = Collections.singletonMap("name", genreName);

        return namedParameterJdbcOperations.query(
                "select id, name from Genre where name = :name", params, new GenreMapper()
        );

    }

    @Override
    public Optional<Genre> getById(long id) {

        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            return namedParameterJdbcOperations.queryForObject(
                    "select id, name from genre where id = :id", params, new OptionalGenreMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");

            return new Genre(id, name);
        }
    }

    private static class OptionalGenreMapper implements RowMapper<Optional<Genre>> {

        @Override
        public Optional<Genre> mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return Optional.of(new Genre(id, name));
        }
    }
}
