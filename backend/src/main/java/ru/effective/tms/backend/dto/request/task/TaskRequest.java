package ru.effective.tms.backend.dto.request.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective.tms.backend.enum_.task.TaskPriority;
import ru.effective.tms.backend.enum_.task.TaskStatus;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TaskRequest {

    private String title;

    private String description;

    private TaskStatus taskStatus;

    private TaskPriority taskPriority;

    private UUID authorId;

    private UUID assigneeId;
}
