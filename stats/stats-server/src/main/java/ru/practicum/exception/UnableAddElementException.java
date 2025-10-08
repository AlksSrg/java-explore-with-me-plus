package ru.practicum.exception;

import lombok.Getter;

@Getter
public class UnableAddElementException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String reason;

	public UnableAddElementException(String errorMessage, Object object) {
		super("Объект " + object.toString() + " не сохранен.");
		this.reason = errorMessage;
	}
}
