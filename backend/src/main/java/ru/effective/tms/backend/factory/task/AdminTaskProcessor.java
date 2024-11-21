package ru.effective.tms.backend.factory.task;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.effective.tms.backend.dto.request.task.UpdateTaskRequest;
import ru.effective.tms.backend.exception.task.UserNotFoundException;
import ru.effective.tms.backend.filter.task.TaskFilter;
import ru.effective.tms.backend.filter.task.TaskSpecification;
import ru.effective.tms.backend.mapper.TaskMapper;
import ru.effective.tms.backend.model.Task;
import ru.effective.tms.backend.model.User;
import ru.effective.tms.backend.repository.TaskRepository;
import ru.effective.tms.backend.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class AdminTaskProcessor implements TaskProcessor {

    private final TaskMapper mapper;

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Override
    public Page<Task> getAll(TaskFilter filter, Pageable pageable) {
        var spec = TaskSpecification.createUserSpecification(filter);
        return taskRepository.findAll(spec, pageable);
    }

    @Override
    public void update(UpdateTaskRequest request, Task task) {
        mapper.updateModelAdmin(request, task);
        setAuthorAndAssignee(request, task);
    }

    private void setAuthorAndAssignee(UpdateTaskRequest request, Task task) {
        setUserIfPresent(request.getAuthorId(), task::setAuthor);
        setUserIfPresent(request.getAssigneeId(), task::setAssignee);
    }

    private void setUserIfPresent(UUID userId, Consumer<User> setUserFunction) {
        Optional.ofNullable(userId)
                .ifPresent(id -> {
                    User user = userRepository.findById(id)
                            .orElseThrow(() -> new UserNotFoundException(id));
                    setUserFunction.accept(user);
                });
    }
}
