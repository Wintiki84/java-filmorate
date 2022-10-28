package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.HashMap;
import java.util.List;

public interface UserStorage {
    List<User> getAll();

    HashMap<Integer, User> getUsers();

    ResponseEntity<User> add(User user);

    ResponseEntity<User> update(User user);

    ResponseEntity<User> findById(int id);
}
