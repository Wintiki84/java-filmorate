package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;
    private Comparator<Film> comparator = Comparator.comparing(obj -> obj.getLikes().size());

    public ResponseEntity<Film> addLike(long idFilm, int userId) {
            if (filmStorage.getFilms().containsKey(idFilm) & userStorage.getUsers().containsKey(userId)) {
                filmStorage.getFilms().get(idFilm).addLike(userId);
                log.info("Пользователь с id={} добавил лайк фильму с id={}", userId, idFilm);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                log.info("Пользователь с id={} или фильм с id={} не найден", userId, idFilm);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    public ResponseEntity<Film> deleteLike(long idFilm, int userId) {
            if (filmStorage.getFilms().containsKey(idFilm) & userStorage.getUsers().containsKey(userId)) {
                filmStorage.getFilms().get(idFilm).deleteLike(userId);
                log.info("Пользователь с id={} удалил лайк фильму с id={}", userId, idFilm);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                log.info("Пользователь с id={} или фильм с id={} не найден", userId, idFilm);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    public ResponseEntity<List<Film>> getMostPopularFilms(int count) {
        List<Film> sortedFilms = new ArrayList<>(filmStorage.getFilms().values());
        sortedFilms.sort(comparator);
        Collections.reverse(sortedFilms);
        log.info("Топ {} фильмов получены", count);
        count = Math.min(count, sortedFilms.size());
        return new ResponseEntity<>(sortedFilms.subList(0, count), HttpStatus.OK);
    }
}
