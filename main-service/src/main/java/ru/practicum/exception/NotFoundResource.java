package ru.practicum.exception;

public class NotFoundResource extends RuntimeException {
    public NotFoundResource(String msg) {
        super(msg);
    }
}