package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class UserService {
    private final InMemoryUserStorage userStorage;

    public ResponseEntity<User> addFriend(int id, int friendId) {
            if (userStorage.getUsers().containsKey(id) & userStorage.getUsers().containsKey(friendId)){
                userStorage.getUsers().get(id).addFriend(friendId);
                userStorage.getUsers().get(friendId).addFriend(id);
                log.info("Пользователь с id={} добавил пользователя с id={} в друзья", id, friendId);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                log.info("Пользователь с ID={} или id={} не найден", id, friendId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    public ResponseEntity<User> deleteFriend(int id, int friendId) {
            if (userStorage.getUsers().containsKey(id) & userStorage.getUsers().containsKey(friendId)){
                userStorage.getUsers().get(id).deleteFriend(friendId);
                userStorage.getUsers().get(friendId).deleteFriend(id);
                log.info("Пользователь с id={} удалил пользователя с id={} из друзей", id, friendId);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                log.info("Пользователь с ID={} или id={} не найден", id, friendId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    public ResponseEntity<Set<User>> getAllFriends(int id) {
            if (userStorage.getUsers().containsKey(id)){
                log.info("Друзья пользователя с id={} получены", id);
                Set<User> friends = new HashSet<>();
                for (int idFriends : userStorage.getUsers().get(id).getFriends()){
                    friends.add(userStorage.getUsers().get(idFriends));
                }
                return new ResponseEntity<>(friends, HttpStatus.OK);
            } else {
                log.info("Пользователь с id={} не найден", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    public ResponseEntity<Set<User>> getMutualFriends(int id, int otherId) {
            if (userStorage.getUsers().containsKey(id) & userStorage.getUsers().containsKey(otherId)){
                Set<User> friends = new HashSet<>();
                Stream<Integer> userStream = userStorage.getUsers().get(id).getFriends().stream();
                userStream.forEach((userId) -> {
                    Stream<Integer> otherUserStream = userStorage.getUsers().get(otherId).getFriends().stream();
                    otherUserStream.forEach((otherUserId) -> {
                        if (Objects.equals(userId, otherUserId)) friends.add(userStorage.getUsers().get(userId));
                    });
                });
                log.info("Общие друзья пользователeй с id={} и id={} получены", id, otherId);
                return new ResponseEntity<>(friends, HttpStatus.OK);
            } else {
                log.info("Пользователь с id={} или id={} не найден", id, otherId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }
}
