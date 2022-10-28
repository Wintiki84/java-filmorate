package ru.yandex.practicum.filmorate.model.user;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface InUser {
    void addFriend(Integer friendId);

    void deleteFriend(Integer friendId);

    Set<Integer> getFriends();
}
