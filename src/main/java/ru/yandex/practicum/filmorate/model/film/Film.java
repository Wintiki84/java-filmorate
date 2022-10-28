package ru.yandex.practicum.filmorate.model.film;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.validators.IsAfter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film implements InFilm {
    @Min(0)
    private long id;
    @NotBlank
    @NotNull
    private String name;
    @Size(max = 200)
    private String description;
    @IsAfter(value = "1895-12-28", message = "Дата должна быть больше 1895-12-28")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    private Set<Integer> likes = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, Integer duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    @Override
    public void addLike(Integer userId) {
        likes.add(userId);
    }

    @Override
    public void deleteLike(Integer userId) {
        likes.remove(userId);
    }

    @Override
    public Set<Integer> getLikes() {
        return likes;
    }
}
