package ru.effective.tms.backend.exception.security;

import org.springframework.http.HttpStatus;
import ru.effective.tms.backend.exception.TMSRuntimeException;

public class SecurityContextException extends TMSRuntimeException {

    public SecurityContextException() {
        super(
                "Could not set user authentication in security context",
                HttpStatus.UNAUTHORIZED
        );
    }
}