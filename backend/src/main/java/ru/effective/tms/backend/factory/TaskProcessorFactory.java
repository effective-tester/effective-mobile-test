package ru.effective.tms.backend.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.effective.tms.backend.enum_.security.UserRole;
import ru.effective.tms.backend.exception.task.ProcessorNotFoundException;
import ru.effective.tms.backend.factory.task.TaskProcessor;
import ru.effective.tms.backend.model.User;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TaskProcessorFactory {

    private final Map<UserRole, TaskProcessor> taskProcessors;

    public TaskProcessor getProcessorFor(User user) {
        return user.getRoles().stream()
                .map(role -> taskProcessors.get(role.getName()))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(ProcessorNotFoundException::new);
    }
}
