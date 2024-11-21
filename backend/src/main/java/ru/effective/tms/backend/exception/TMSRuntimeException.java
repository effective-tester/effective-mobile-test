package ru.effective.tms.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class TMSRuntimeException extends RuntimeException {

    private final HttpStatus statusCode;

    protected TMSRuntimeException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
