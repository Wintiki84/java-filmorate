package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.IdGeneratorUser;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private HashMap<Long, User> users= new HashMap<>();

    @GetMapping
    public List<User> getUsers() {
        log.info("Список пользователей отправлен");
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user)  {
        if (!users.containsKey(user.getId())) {
            user.setId(IdGeneratorUser.getNewId());
            users.put(user.getId(), user);
            log.info("Пользователь добавлен");
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Данные пользователя обновлены");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
