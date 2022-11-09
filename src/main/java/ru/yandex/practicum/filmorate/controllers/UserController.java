package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {
    private final UserService userService;
    private final FilmService filmService;

    @Autowired
    public UserController(UserService userService, FilmService filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Returned all users");
        return userService.getAllUsers();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        User result = userService.addUser(userService.checkName(user));
        log.info("Пользователь добавлен {}", result.getName());
        return result;
    }

    @PutMapping
    public User changeUser(@Valid @RequestBody User user) {
        log.info("пользователь обновлен {}", user.getId());
        return userService.changeUser(userService.checkName(user));
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addToFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Пользователь {} добавил в друзья {}", friendId, id);
        userService.addFriendToUser(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteUserFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Пользователь {} удалил из друзей {}", friendId, id);
        userService.deleteFromFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable long id) {
        log.info("Получены все друзья {} пользователя", id);
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info("Получены общие друзья между пользователем {} и пользователем {}", id, otherId);
        return userService.getCommonFriend(id, otherId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        log.info("Получен пользователь {}", id);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") long id) {
        log.info("Удален пользователь {}", id);
        userService.deleteUser(id);
    }
}