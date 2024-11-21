package ru.effective.tms.backend.controller.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.effective.tms.backend.TestEnvironment;
import ru.effective.tms.backend.enum_.security.UserRole;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteMappingTest extends TestEnvironment {

    @BeforeEach
    public void setup() throws Exception {
        registerAll();
    }

    @Test
    public void deleteTask_shouldReturnNo_whenAdminRequest() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.ADMIN);
        performDeleteRequestAs(TASK_URL, taskId, UserRole.ADMIN)
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTask_shouldReturnNotFound_whenDeletedSameTask() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.ADMIN);
        performDeleteRequestAs(TASK_URL, taskId, UserRole.ADMIN);
        performDeleteRequestAs(TASK_URL, taskId, UserRole.ADMIN)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value(TASK_NOT_FOUND_ERROR + taskId));
    }

    @Test
    public void deleteTask_shouldReturnForbidden_whenUserRequest() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.USER);
        performDeleteRequestAs(TASK_URL, taskId, UserRole.USER)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error")
                        .value(ACCESS_DENIED_ERROR));
    }

    @Test
    public void deleteTask_shouldReturnUnauthorized_whenNotAuthorized() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.USER);
        performDeleteRequest(TASKS_URL, taskId)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error")
                        .value(UNAUTHORIZED_ERROR));
    }
}

