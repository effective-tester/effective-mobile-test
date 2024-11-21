package ru.effective.tms.backend.factory.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.effective.tms.backend.dto.request.task.UpdateTaskRequest;
import ru.effective.tms.backend.filter.task.TaskFilter;
import ru.effective.tms.backend.model.Task;

public interface TaskProcessor {

    Page<Task> getAll(TaskFilter filter, Pageable pageable);

    void update(UpdateTaskRequest request, Task task);
}
