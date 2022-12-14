package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class LikesDbStorage implements LikesStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void like(long id, long userId) {
        String sql = "INSERT INTO likes(film_id, user_id)" +
                "VALUES(?, ?)";
        jdbcTemplate.update(sql, id, userId);
        updateRate(id);
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        String sql = "DELETE FROM likes " +
                "WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
        updateRate(filmId);
    }

    @Override
    public Set<Long> getLikes(long filmId) {
        String sql = "SELECT user_id " +
                "FROM likes " +
                "WHERE film_id = ?";
        List<Long> likes = jdbcTemplate.query(sql, LikesDbStorage::mapRowToLong, filmId);
        return new HashSet<>(likes);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        String sql = "SELECT * FROM film f " +
                "LEFT JOIN mpa m ON f.mpa_id = m.mpa_id " +
                "ORDER BY f.rate DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sql, FilmDbStorage::mapRowToFilm, count);
    }

    private static long mapRowToLong(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getLong("user_id");
    }

    @Override
    public void updateRate(long filmId) {
        String sql = "UPDATE film f SET rate = " +
                "(SELECT COUNT(l.user_id) FROM likes l WHERE l.film_id = f.film_id) " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sql, filmId);
    }

    private long makeFilmId(ResultSet rs, int rowNum) throws SQLException {
        return rs.getLong("film_id");
    }
}