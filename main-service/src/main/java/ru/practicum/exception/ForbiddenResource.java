package ru.practicum.exception;

public class ForbiddenResource extends RuntimeException {
    public ForbiddenResource(String msg) {
        super(msg);
    }
}
