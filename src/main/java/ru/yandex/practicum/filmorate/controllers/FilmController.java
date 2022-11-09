package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Validated
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Отправлены все фильмы");
        return filmService.getFilms();
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (filmService.checkDate(film)) {
            log.info("Фильм {} добавлен", film.getName());
            return filmService.addFilm(film);
        } else {
            throw new ValidationException("Wrong release date");
        }
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable long id) {
        log.info("Фильм отправлен {}", id);
        return filmService.getFilmById(id);
    }

    @PutMapping("/films")
    public Film changeFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (filmService.checkDate(film)) {
            log.info("Фильм {} обновлен", film.getId());
            return filmService.changeFilm(film);
        } else {
            throw new ValidationException("ошибочная дата релиза");
        }
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void like(@PathVariable long id, @PathVariable long userId) {
        log.info("Пользователь {} добавил лайк фильму {}", userId, id);
        filmService.like(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        log.info("Пользователь {} Удалил лайк фильму {}", userId, id);
        filmService.deleteLike(id, userId);
    }

    @DeleteMapping("/films/{id}")
    public void deleteFilmById(@PathVariable long id) {
        log.info("Удален фильм {}", id);
        filmService.deleteFilm(id);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count){
        log.info("Отправлены самые популярные фильмы");
        return filmService.getPopularFilms(count);
    }
}