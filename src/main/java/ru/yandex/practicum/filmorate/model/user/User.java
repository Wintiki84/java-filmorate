package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;
import ru.yandex.practicum.filmorate.validators.DoesNotContainSpace;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

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
    private Set<Long> friends = new HashSet<>();

    public User(String login, String name, String email, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name == null ? login : name.equals("") ? login : name;
        this.birthday = birthday;
    }

    public void addFriend(long friendId) {
        friends.add(friendId);
    }

    public void deleteFriend(long friendId) {
        friends.remove(friendId);
    }

    public Set<Long> getFriends() {
        return friends;
    }
}

