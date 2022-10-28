package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();
    private long id = 1L;

    @Override
    public List<Film> getAll() {
        log.info("Список фильмов отправлен");
        return new ArrayList<>(films.values());
    }

    @Override
    public HashMap<Long, Film> getFilms() {
        return films;
    }

    @Override
    public ResponseEntity<Film> add(Film film) {
        if (!films.containsKey(film.getId())) {
            film.setId(id++);
            films.put(film.getId(), film);
            log.info("Фильм добавлен");
            return new ResponseEntity<>(film, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Override
    public ResponseEntity<Film> update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Данные фильма обновлены");
            return new ResponseEntity<>(film, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Film> findById(@PathVariable long id) {
        if (films.containsKey(id)) {
            Film film = films.get(id);
            log.info("Фильм с ID " + id + " отправлен клиенту");
            return new ResponseEntity<>(film, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
