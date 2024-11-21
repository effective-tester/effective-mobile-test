package ru.effective.tms.backend.dto.request.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.effective.tms.backend.enum_.task.TaskPriority;
import ru.effective.tms.backend.enum_.task.TaskStatus;
import ru.effective.tms.backend.validation.CreateGroup;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequest extends TaskRequest {

    @NotBlank(groups = CreateGroup.class)
    private String title;

    private String description;

    @NotNull(groups = CreateGroup.class)
    private TaskStatus status;

    @NotNull(groups = CreateGroup.class)
    private TaskPriority priority;

    private UUID authorId;

    private UUID assigneeId;
}


