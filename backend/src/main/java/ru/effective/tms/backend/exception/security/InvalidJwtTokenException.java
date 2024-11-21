package ru.effective.tms.backend.exception.security;

import org.springframework.http.HttpStatus;
import ru.effective.tms.backend.exception.TMSRuntimeException;

public class InvalidJwtTokenException extends TMSRuntimeException {

    public InvalidJwtTokenException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}