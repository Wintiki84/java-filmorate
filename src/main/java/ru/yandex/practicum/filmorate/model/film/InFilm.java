package ru.yandex.practicum.filmorate.model.film;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface InFilm {
    void addLike(Integer userId);

    void deleteLike(Integer userId);

    Set<Integer> getLikes();
}
