package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ModelNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public Genre getGenreById(int id) {
        if (getAllGenres().stream().anyMatch(x -> x.getId() == id)) {
            return genreStorage.getGenreById(id);
        } else {
            throw new ModelNotFoundException("Жанр не найден " + id);
        }
    }

    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }
}