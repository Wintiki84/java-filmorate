package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validators.DoesNotContainSpace;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private long id;
    @Email(message = "Неверный формат Email")
    private String email;
    @NotBlank(message = "Не должно быть пустым")
    @DoesNotContainSpace(value = " ", message = "Не должно быть пробелов")
    private String login;
    private String name;
    @PastOrPresent(message = "День рождения не может быть в будущем")
    @NotNull
    private LocalDate birthday;

    public User(String login, String name, String email, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name == null ? login : name.equals("") ? login : name;
        this.birthday = birthday;
    }
}

