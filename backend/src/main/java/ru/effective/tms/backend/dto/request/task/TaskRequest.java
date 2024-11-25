package ru.effective.tms.backend.dto.request.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.effective.tms.backend.enum_.task.TaskPriority;
import ru.effective.tms.backend.enum_.task.TaskStatus;

import java.util.UUID;

@Setter
@Getter
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
