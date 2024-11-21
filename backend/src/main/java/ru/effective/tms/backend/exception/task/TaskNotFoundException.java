package ru.effective.tms.backend.exception.task;

import org.springframework.http.HttpStatus;
import ru.effective.tms.backend.exception.TMSRuntimeException;

import java.util.UUID;

public class TaskNotFoundException extends TMSRuntimeException {

    public TaskNotFoundException(UUID id) {
        super(
                "Task not found with id: " + id,
                HttpStatus.NOT_FOUND
        );
    }
}