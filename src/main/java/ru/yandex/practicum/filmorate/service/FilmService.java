package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ModelNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;
    private final LikesStorage likesStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage,
                       UserStorage userStorage,
                       GenreStorage genreStorage,
                       LikesStorage likesStorage) {
        this.filmStorage = filmStorage;
        this.genreStorage = genreStorage;
        this.likesStorage = likesStorage;
        this.userStorage = userStorage;
    }

    public Film addFilm(Film film) {
        long idFilm = filmStorage.addFilm(film);
        genreStorage.addGenresToFilm(film, idFilm);
        film.setId(idFilm);
        return getFilmById(idFilm);
    }

    public Film getFilmById(long id) {
        Film film;
        try {
            film = filmStorage.getFilmById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ModelNotFoundException("Film wasn't found");
        }
        film.setLikes(likesStorage.getLikes(id));
        film.setGenres(getGenresByFilmId(id));
        return film;
    }

    private Set<Genre> getGenresByFilmId(long filmId) {
        return new HashSet<>(genreStorage.getFilmGenres(filmId));
    }

    public Film changeFilm(Film film) {
        try {
            filmStorage.getFilmById(film.getId());
        } catch (EmptyResultDataAccessException ex) {
            throw new ModelNotFoundException("Film wasn't found");
        }
        filmStorage.changeFilm(film);
        genreStorage.changeFilmGenres(film);
        return film;
    }

    public void like(long filmId, long userId) {
        likesStorage.like(filmId, userId);
    }

    public void deleteLike(long filmId, long userId) {
        Film film = getFilmById(filmId);
        if (film.getLikes().contains(userId)) {
            likesStorage.deleteLike(filmId, userId);
        } else {
            throw new ModelNotFoundException("User not found with id " + userId);
        }
    }

    public List<Film> getFilms() {
        List<Film> films = filmStorage.getFilms();
        films.forEach(this::constructFilm);
        return films;
    }

    public List<Film> getPopularFilms(int count) {
        List<Film> films = likesStorage.getPopularFilms(count);
        films.forEach(this::constructFilm);
        return films;
    }

    public boolean checkDate(Film film) {
        return film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28));
    }

    public void deleteFilm(long id) {
        getFilmById(id);
        filmStorage.deleteFilm(id);
    }

    private void constructFilm(Film film) {
        film.setGenres(getGenresByFilmId(film.getId()));
        film.setLikes(likesStorage.getLikes(film.getId()));
    }
}