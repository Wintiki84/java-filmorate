package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class InMemoryUserStorage implements UserStorage{
    private final HashMap<Integer, User> users= new HashMap<>();
    private int id = 1;

    @Override
    public List<User> getAll() {
        log.info("Список пользователей отправлен");
        return new ArrayList<>(users.values());
    }

    @Override
    public HashMap<Integer, User> getUsers(){
        return users;
    }

    @Override
    public ResponseEntity<User> add(User user) {
        if (!users.containsKey(user.getId())) {
            user.setId(id++);
            users.put(user.getId(), user);
            log.info("Пользователь добавлен");
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Override
    public ResponseEntity<User> update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Данные пользователя обновлены");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<User> findById(@PathVariable int id) {
        if (users.containsKey(id)) {
            User user = users.get(id);
            log.info("Пользователь с ID " + id + " отправлен клиенту");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
