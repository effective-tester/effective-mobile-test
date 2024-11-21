package ru.effective.tms.backend.factory.task;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.effective.tms.backend.dto.request.task.UpdateTaskRequest;
import ru.effective.tms.backend.filter.task.TaskFilter;
import ru.effective.tms.backend.filter.task.TaskSpecification;
import ru.effective.tms.backend.mapper.TaskMapper;
import ru.effective.tms.backend.model.Task;
import ru.effective.tms.backend.model.User;
import ru.effective.tms.backend.repository.TaskRepository;
import ru.effective.tms.backend.util.UserUtil;

@Component
@RequiredArgsConstructor
public class UserTaskProcessor implements TaskProcessor {

    private final TaskMapper taskMapper;

    private final TaskRepository taskRepository;

    private final UserUtil userUtil;

    @Override
    public Page<Task> getAll(TaskFilter filter, Pageable pageable) {
        User user = userUtil.getCurrentUser();
        var spec = TaskSpecification.createUserSpecification(user, filter);
        return taskRepository.findAll(spec, pageable);
    }

    @Override
    public void update(UpdateTaskRequest request, Task task) {
        taskMapper.updateModelUser(request, task);
    }
}
