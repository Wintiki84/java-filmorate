package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.IdGeneratorFilm;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private HashMap<Long, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> getFilms() {
        log.info("Список фильмов отправлен");
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            film.setId(IdGeneratorFilm.getNewId());
            films.put(film.getId(), film);
            log.info("Фильм добавлен");
            return new ResponseEntity<>(film, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Данные фильма обновлены");
            return new ResponseEntity<>(film, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

