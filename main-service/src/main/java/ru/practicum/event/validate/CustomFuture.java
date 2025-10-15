package ru.practicum.event.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomFutureValidator.class)
@Target({ElementType.FIELD })
public @interface CustomFuture {
    String message() default
            "Дата должна быть не ранее текущей + 2 часа";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
