package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.FriendDbStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User changeUser(User user) {
        userStorage.findUserById(user.getId());
        return userStorage.changeUser(user);
    }

    public void addFriendToUser(long userId, long friendId) {
        userStorage.findUserById(userId);
        userStorage.findUserById(friendId);
        friendStorage.addFriendToUser(userId, friendId);
    }

    public void deleteFromFriend(long userId, long friendId) {
        userStorage.findUserById(userId);
        userStorage.findUserById(friendId);
        friendStorage.deleteFromFriends(userId, friendId);
    }

    public List<User> getUserFriends(long id) {
        userStorage.findUserById(id);
        List<User> userFriends = new ArrayList<>();
        List<Long> friendsId = friendStorage.getUserFriends(id);
        for (Long oneId : friendsId) {
            userFriends.add(getUserById(oneId));
        }
        return userFriends;
    }

    public List<User> getCommonFriend(long id, long otherId) {
        List<User> user = getUserFriends(id);
        List<User> anotherUser = getUserFriends(otherId);
        return user.stream().filter(anotherUser::contains).collect(Collectors.toList());
    }

    public User getUserById(long id) {
        return userStorage.findUserById(id);
    }

    public User checkName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }

    public void deleteUser(long id) {
        userStorage.findUserById(id);
        userStorage.deleteUser(id);
    }
}