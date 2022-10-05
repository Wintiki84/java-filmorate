package ru.yandex.practicum.filmorate.service;

public class IdGeneratorFilm {
    private static int taskId;

    public static int getNewId() {
        taskId++;
        return taskId;
    }
}

