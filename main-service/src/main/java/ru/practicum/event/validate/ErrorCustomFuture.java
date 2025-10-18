package ru.practicum.event.validate;

import jakarta.validation.ConstraintDeclarationException;
import ru.practicum.exception.ConflictResource;

public class ErrorCustomFuture extends ConstraintDeclarationException {
    private final ConflictResource conflictResource;

    public ErrorCustomFuture(ConflictResource conflictResource) {
        super();
        this.conflictResource = conflictResource;
    }

    public ErrorCustomFuture(ConflictResource conflictResource, String message, Object... args) {
        super(String.format(message, args));
        this.conflictResource = conflictResource;
    }
}
