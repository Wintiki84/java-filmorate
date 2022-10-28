package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.HashMap;
import java.util.List;

public interface FilmStorage {
    List<Film> getAll();

    HashMap<Long, Film> getFilms();

    ResponseEntity<Film> add(Film film);

    ResponseEntity<Film> update(Film film);

    ResponseEntity<Film> findById(long id);
}
