package ru.practicum.exception;

public class ConflictResource extends RuntimeException {
    public ConflictResource(String msg) {
        super(msg);
    }
}