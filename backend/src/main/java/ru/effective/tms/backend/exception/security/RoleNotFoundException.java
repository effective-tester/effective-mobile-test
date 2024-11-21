package ru.effective.tms.backend.exception.security;

import org.springframework.http.HttpStatus;
import ru.effective.tms.backend.exception.TMSRuntimeException;

public class RoleNotFoundException extends TMSRuntimeException {

    public RoleNotFoundException() {
        super(
                "Role not found",
                HttpStatus.NOT_FOUND
        );
    }
}