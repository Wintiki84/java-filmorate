package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.validators.DoesNotContainSpace;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {

    @EqualsAndHashCode.Exclude
    private long id = 0;
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

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User() {
        id = 0;
    }

    public void addFriend(long id) {
        friends.add(id);
    }

    public void deleteFriend(long id) {
        friends.remove(id);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("login", login);
        values.put("user_name", name);
        values.put("birth_date", birthday);
        return values;
    }
}