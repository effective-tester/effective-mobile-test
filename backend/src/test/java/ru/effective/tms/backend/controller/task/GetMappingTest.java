package ru.effective.tms.backend.controller.task;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.effective.tms.backend.TestEnvironment;
import ru.effective.tms.backend.enum_.security.UserRole;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetMappingTest extends TestEnvironment {

    @BeforeEach
    public void setup() throws Exception {
        registerAll();
    }

    @Test
    public void getTask_shouldReturnOk_whenAdminRequest() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.USER);
        performGetRequestAs(TASK_URL, taskId, UserRole.ADMIN)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(taskId))
                .andExpect(jsonPath("$.title")
                        .value(TITLE));
    }

    @Test
    public void getTask_shouldReturnOk_whenUserIsAssignee() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.USER);
        performGetRequestAs(TASK_URL, taskId, UserRole.USER)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(taskId))
                .andExpect(jsonPath("$.title")
                        .value(TITLE));
    }

    @Test
    public void getTask_shouldReturnForbidden_whenUserIsNotAssignee() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.ADMIN);
        performGetRequestAs(TASK_URL, taskId, UserRole.USER)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error")
                        .value(ACCESS_DENIED_ERROR));
    }

    @Test
    public void getTask_shouldReturnUnauthorized_whenNotAuthenticated() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.USER);
        performGetRequest(TASK_URL, taskId)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error")
                        .value(UNAUTHORIZED_ERROR));
    }
}
