package ru.effective.tms.backend.exception.security;

import org.springframework.http.HttpStatus;
import ru.effective.tms.backend.exception.TMSRuntimeException;

public class EmailAlreadyInUseException extends TMSRuntimeException {

    public EmailAlreadyInUseException() {
        super(
                "Email already in use",
                HttpStatus.BAD_REQUEST
        );
    }

}