package ru.otus.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.app.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insertNewGenre(Genre genre) {
        namedParameterJdbcOperations.update("insert into Genre (name) values (:name)",
                Map.of("name", genre.getName()));
    }

    @Override
    public Optional<Genre> getGenreByName(String genreName) {
        Map<String, Object> params = Collections.singletonMap("name", genreName);

        try {
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                    "select id, name from Genre where name = :name", params, new GenreMapper()
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Genre> getGenreById(long id) {

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
