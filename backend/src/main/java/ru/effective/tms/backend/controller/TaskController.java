package ru.effective.tms.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective.tms.backend.annotation.security.IsAdmin;
import ru.effective.tms.backend.annotation.security.IsAdminOrUser;
import ru.effective.tms.backend.annotation.security.IsAdminOrUserAssignee;
import ru.effective.tms.backend.dto.param.TaskQueryParams;
import ru.effective.tms.backend.dto.request.task.CreateTaskRequest;
import ru.effective.tms.backend.dto.request.task.UpdateTaskRequest;
import ru.effective.tms.backend.dto.response.TaskDto;
import ru.effective.tms.backend.service.TaskService;
import ru.effective.tms.backend.validation.CreateGroup;
import ru.effective.tms.backend.validation.UpdateGroup;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @IsAdmin
    public ResponseEntity<TaskDto> create(
            @Validated(CreateGroup.class) @RequestBody CreateTaskRequest request
    ) {
        TaskDto task = taskService.createTask(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(task);
    }

    @GetMapping("/{taskId}")
    @IsAdminOrUserAssignee
    public ResponseEntity<TaskDto> get(@PathVariable UUID taskId) {
        TaskDto task = taskService.getTask(taskId);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{taskId}")
    @IsAdminOrUserAssignee
    public ResponseEntity<TaskDto> update(
            @PathVariable UUID taskId,
            @Validated(UpdateGroup.class) @RequestBody UpdateTaskRequest request
    ) {
        TaskDto task = taskService.updateTask(taskId, request);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{taskId}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @IsAdminOrUser
    public ResponseEntity<Page<TaskDto>> getAll(
            @ModelAttribute TaskQueryParams params
    ) {
        Page<TaskDto> tasks = taskService.getAllTasks(params);
        return ResponseEntity.ok(tasks);
    }
}


