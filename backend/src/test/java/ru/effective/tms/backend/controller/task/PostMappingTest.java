package ru.effective.tms.backend.controller.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.effective.tms.backend.TestEnvironment;
import ru.effective.tms.backend.dto.request.task.CreateTaskRequest;
import ru.effective.tms.backend.enum_.security.UserRole;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostMappingTest extends TestEnvironment {

    private CreateTaskRequest createTaskRequest;

    @BeforeEach
    public void setup() throws Exception {
        registerAll();
        createTaskRequest = createTaskRequest();
    }

    @Test
    public void createTask_shouldReturnCreated_whenAdminRequest() throws Exception {
        performPostRequestAs(TASKS_URL, createTaskRequest, UserRole.ADMIN)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title")
                        .value(createTaskRequest.getTitle()))
                .andExpect(jsonPath("$.status")
                        .value(createTaskRequest.getStatus().name()))
                .andExpect(jsonPath("$.priority")
                        .value(createTaskRequest.getPriority().name()));
    }

    @Test
    public void createTask_shouldReturnForbidden_whenUserRequest() throws Exception {
        performPostRequestAs(TASKS_URL, createTaskRequest, UserRole.USER)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error")
                        .value(ACCESS_DENIED_ERROR));
    }

    @Test
    public void createTask_shouldReturnUnauthorized_whenNotAuthorized() throws Exception {
        performPostRequest(TASKS_URL, createTaskRequest)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error")
                        .value(UNAUTHORIZED_ERROR));
    }
}

