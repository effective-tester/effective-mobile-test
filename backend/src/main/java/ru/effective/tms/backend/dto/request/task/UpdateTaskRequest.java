package ru.effective.tms.backend.dto.request.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.effective.tms.backend.enum_.task.TaskPriority;
import ru.effective.tms.backend.enum_.task.TaskStatus;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskRequest extends TaskRequest {

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private UUID authorId;

    private UUID assigneeId;
}
