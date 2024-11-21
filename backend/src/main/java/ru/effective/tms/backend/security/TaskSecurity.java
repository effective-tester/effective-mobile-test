package ru.effective.tms.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.effective.tms.backend.model.Task;
import ru.effective.tms.backend.repository.TaskRepository;

import java.util.UUID;

@Component("taskSecurity")
@RequiredArgsConstructor
public class TaskSecurity {

    private final TaskRepository taskRepository;

    public boolean isAssignee(Authentication authentication, UUID taskId) {
        String email = authentication.getName();
        return taskRepository.findById(taskId)
                .map(Task::getAssignee)
                .map(assignee -> assignee.getEmail().equals(email))
                .orElse(false);
    }
}
