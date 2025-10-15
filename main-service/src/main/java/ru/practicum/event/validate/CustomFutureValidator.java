package ru.practicum.event.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class CustomFutureValidator implements ConstraintValidator<CustomFuture, LocalDateTime> {
    @Override
    public void initialize(CustomFuture constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        return localDateTime.isAfter(LocalDateTime.now().plusHours(2));
    }
}
