package ot.homework5plus.rushm.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.dao.AuthorDao;
import ot.homework5plus.rushm.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoImpl implements AuthorDao {
    private static final int INITIAL_CAPACITY = 1;
    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void insert(Author genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into author (name) values(:name)", params, keyHolder);
    }

    @Override
    public Author getById(long id) {
        final Map<String, Object> params = new HashMap<>(INITIAL_CAPACITY);
        params.put("id", id);
        return jdbcOperations.queryForObject("select * from author where id = :id", params, new AuthorMapper());
    }

    @Override
    public boolean checkByName(String genreName) {
        final Map<String, Object> params = new HashMap<>(INITIAL_CAPACITY);
        params.put("name", genreName);
        List queryResult = jdbcOperations.query("select * from author where name = :name", params, new AuthorMapper());
        return queryResult.size() != 0;
    }

    @Override
    public Author getByName(String authorName) {
        final Map<String, Object> params = new HashMap<>(INITIAL_CAPACITY);
        params.put("name", authorName);
        return jdbcOperations.queryForObject("select * from author where name = :name", params, new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
