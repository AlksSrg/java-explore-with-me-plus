package ru.practicum.event.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.exception.ConflictResource;

import java.time.LocalDateTime;

public class CustomFutureValidator implements ConstraintValidator<CustomFuture, LocalDateTime> {
    @Override
    public void initialize(CustomFuture constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (!localDateTime.isAfter(LocalDateTime.now().plusHours(2)))
            throw new ErrorCustomFuture(new ConflictResource("Дата должна быть не ранее текущей + 2 часа"),
                    "Дата должна быть не ранее текущей + 2 часа", localDateTime);

        return true; //localDateTime.isAfter(LocalDateTime.now().plusHours(2));
    }
}
