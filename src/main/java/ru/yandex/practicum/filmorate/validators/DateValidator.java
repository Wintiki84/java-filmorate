package ru.yandex.practicum.filmorate.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator implements ConstraintValidator<IsAfter, LocalDate> {
    private LocalDate minDate;
    private final DateTimeFormatter formatOfDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(IsAfter constraintAnnotation) {
        String dateStr = constraintAnnotation.value();
        minDate = LocalDate.parse(dateStr, formatOfDate);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(minDate);
    }
}