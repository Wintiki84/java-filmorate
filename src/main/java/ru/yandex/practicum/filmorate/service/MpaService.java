package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ModelNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;

    public Mpa getMpaById(int id) {
        if (getAllMpa().stream().anyMatch(x -> x.getId() == id)) {
            return mpaStorage.getMpaById(id);
        } else {
            throw new ModelNotFoundException("МПА не найден " + id);
        }
    }

    public List<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }
}