package ru.effective.tms.backend.controller.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.effective.tms.backend.TestEnvironment;
import ru.effective.tms.backend.dto.request.task.CreateTaskRequest;
import ru.effective.tms.backend.dto.request.task.UpdateTaskRequest;
import ru.effective.tms.backend.enum_.security.UserRole;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PutMappingTest extends TestEnvironment {

    private CreateTaskRequest createTaskRequest;

    private UpdateTaskRequest updateTaskRequest;

    @BeforeEach
    public void setup() throws Exception {
        registerAll();
        createTaskRequest = createTaskRequest();
        updateTaskRequest = updateTaskRequest();
    }

    @Test
    public void updateTask_shouldReturnOk_whenAdminRequest() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.USER);
        performPutRequestAs(TASK_URL, taskId, updateTaskRequest, UserRole.ADMIN)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(taskId))
                .andExpect(jsonPath("$.title")
                        .value(updateTaskRequest.getTitle()))
                .andExpect(jsonPath("$.priority")
                        .value(updateTaskRequest.getPriority().name()))
                .andExpect(jsonPath("$.status")
                        .value(updateTaskRequest.getStatus().name()));
    }

    @Test
    public void updateTask_shouldReturnOk_whenUserIsAssignee() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.USER);
        performPutRequestAs(TASK_URL, taskId, updateTaskRequest, UserRole.USER)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(taskId))
                .andExpect(jsonPath("$.title")
                        .value(createTaskRequest.getTitle()))
                .andExpect(jsonPath("$.priority")
                        .value(createTaskRequest.getPriority().name()))
                .andExpect(jsonPath("$.status")
                        .value(updateTaskRequest.getStatus().name()));
    }

    @Test
    public void updateTask_shouldReturnForbidden_whenUserIsNotAssignee() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.ADMIN);
        performPutRequestAs(TASK_URL, taskId, updateTaskRequest, UserRole.USER)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error")
                        .value(ACCESS_DENIED_ERROR));
    }

    @Test
    public void updateTask_shouldReturnUnauthorized_whenNotAuthenticated() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.USER);
        performPutRequest(TASK_URL, taskId, updateTaskRequest)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error")
                        .value(UNAUTHORIZED_ERROR));
    }
}
