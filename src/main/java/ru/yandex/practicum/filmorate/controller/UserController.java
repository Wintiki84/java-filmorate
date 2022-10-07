package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private HashMap<Long, User> users= new HashMap<>();
    private long id = 1L;

    @GetMapping
    public List<User> get() {
        log.info("Список пользователей отправлен");
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public ResponseEntity<User> add(@Valid @RequestBody User user)  {
        if (!users.containsKey(user.getId())) {
            user.setId(id++);
            users.put(user.getId(), user);
            log.info("Пользователь добавлен");
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Данные пользователя обновлены");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
