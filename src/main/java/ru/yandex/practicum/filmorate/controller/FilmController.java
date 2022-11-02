package ru.yandex.practicum.filmorate.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
@Data
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public List<Film> get() {
        return filmService.getFilmStorage().getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> findById(@PathVariable("id") long id) {
        return filmService.getFilmStorage().findById(id);
    }

    @PostMapping
    public ResponseEntity<Film> add(@Valid @RequestBody Film film) {
        return filmService.getFilmStorage().add(film);
    }

    @PutMapping
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) {
        return filmService.getFilmStorage().update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> addLike(@Valid @PathVariable long id, @PathVariable long userId) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> deleteFriend(@Valid @PathVariable long id, @PathVariable long userId) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    @ResponseBody
    public ResponseEntity<List<Film>> getMostPopularFilms(@RequestParam(required = false, defaultValue = "10",
            name = "count") Integer count) {
        return filmService.getMostPopularFilms(count);
    }
}

