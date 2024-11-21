package ru.effective.tms.backend.exception.task;

import org.springframework.http.HttpStatus;
import ru.effective.tms.backend.exception.TMSRuntimeException;

public class ProcessorNotFoundException extends TMSRuntimeException {

    public ProcessorNotFoundException() {
        super(
                "Processor not found",
                HttpStatus.NOT_FOUND
        );
    }
}