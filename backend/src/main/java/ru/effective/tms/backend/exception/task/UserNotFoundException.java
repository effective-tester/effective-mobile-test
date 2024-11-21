package ru.effective.tms.backend.exception.task;

import org.springframework.http.HttpStatus;
import ru.effective.tms.backend.exception.TMSRuntimeException;

import java.util.UUID;

public class UserNotFoundException extends TMSRuntimeException {

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException() {
        super(
                "User not found",
                HttpStatus.NOT_FOUND
        );
    }

    public UserNotFoundException(UUID id) {
        super(
                "User not found with id: " + id,
                HttpStatus.NOT_FOUND
        );
    }
}