package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;

@Getter
public class IncorrectParameterException extends RuntimeException {
    private final Long id;
    private final String object;

    public IncorrectParameterException(Long id, String object) {
        this.id = id;
        this.object = object;
    }

    @Override
    public String getMessage() {
        return "Вызов несуществующего " + object + " c id = " + id;
    }
}
