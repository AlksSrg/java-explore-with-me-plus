package ru.practicum.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EndpointHitHandler {
	
	private final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

	@ExceptionHandler
	public ResponseError unableAddElementExceptionHandler (final UnableAddElementException exception) {

		return ResponseError.builder()
				.errorMessage(exception.getMessage())
				.reason(exception.getReason())
				.timestamp(getTimeStamp())
				.build();
		
	}

	private String getTimeStamp() {
		return LocalDateTime.now().format(dateTimeFormatter);
	}
}
