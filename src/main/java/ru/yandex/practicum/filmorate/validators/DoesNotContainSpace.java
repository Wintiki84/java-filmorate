package ru.yandex.practicum.filmorate.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = LoginValidator.class)
public @interface DoesNotContainSpace {
    String message() default "Логин не должен содержать пробелов";
    String value();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
