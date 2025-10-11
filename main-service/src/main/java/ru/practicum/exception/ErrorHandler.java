package ru.practicum.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    private static final DateTimeFormatter FORMAT_DATE_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String convertStackTraceToString(Exception ex) {
        Writer stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundResource(NotFoundResource ex) {
        log.error(convertStackTraceToString(ex));
        return ApiError.builder()
                .message(ex.getMessage())
                .reason("Запрашиваемый объект не найден")
                .status(HttpStatus.NOT_FOUND.toString())
                .timestamp(LocalDateTime.now().format(FORMAT_DATE_TIME))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictResource(ConflictResource ex) {
        log.error(convertStackTraceToString(ex));
        return ApiError.builder()
                .message(ex.getMessage())
                .reason("Нарушено ограничение целостности")
                .status(HttpStatus.NOT_FOUND.toString())
                .timestamp(LocalDateTime.now().format(FORMAT_DATE_TIME))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiError handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(convertStackTraceToString(ex));
        return ApiError.builder()
                .message(ex.getMessage())
                .reason("Некорректные параметры")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now().format(FORMAT_DATE_TIME))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiError handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(convertStackTraceToString(ex));
        return ApiError.builder()
                .message(ex.getMessage())
                .reason("Некорректные параметры")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now().format(FORMAT_DATE_TIME))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(Exception ex) {
        log.error(convertStackTraceToString(ex));
        return ApiError.builder()
                .message(ex.getMessage())
                .reason(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timestamp(LocalDateTime.now().format(FORMAT_DATE_TIME))
                .build();
    }
}
