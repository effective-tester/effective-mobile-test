package ru.effective.tms.backend.controller.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.effective.tms.backend.TestEnvironment;
import ru.effective.tms.backend.enum_.security.UserRole;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllMappingTest extends TestEnvironment {

    @BeforeEach
    public void setup() throws Exception {
        registerAll();
    }

    @Test
    public void getAllTasks_shouldReturnOk_whenAdminRequest() throws Exception {
        var adminTasksNumber = 4;
        var userTasksNumber = 5;
        var adminTasks = tasksWithAssignee(UserRole.ADMIN, adminTasksNumber);
        var userTasks = tasksWithAssignee(UserRole.USER, userTasksNumber);
        performGetRequestAs(TASKS_URL, UserRole.ADMIN)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()")
                        .value(adminTasks.size() + userTasks.size()));
    }

    @Test
    public void getAllTasks_shouldReturnOk_whenUserIsAssignee() throws Exception {
        var adminTasksNumber = 4;
        var userTasksNumber = 5;
        var adminTasks = tasksWithAssignee(UserRole.ADMIN, adminTasksNumber);
        var userTasks = tasksWithAssignee(UserRole.USER, userTasksNumber);
        performGetRequestAs(TASKS_URL, UserRole.USER)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()")
                        .value(userTasks.size()));
    }

    @Test
    public void getAllTasks_shouldReturnCorrectTotals_whenUser() throws Exception {
        var adminTasksNumber = 13;
        var adminTasks = tasksWithAssignee(UserRole.ADMIN, adminTasksNumber);
        performGetRequestAs(TASKS_URL, UserRole.ADMIN)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()")
                        .value(Math.min(PAGE_SIZE, adminTasks.size())))
                .andExpect(jsonPath("$.totalElements")
                        .value(adminTasks.size()))
                .andExpect(jsonPath("$.totalPages")
                        .value(adminTasks.size() / PAGE_SIZE + 1));
    }

    @Test
    public void getAllTasks_shouldReturnUnauthorized_whenNotAuthenticated() throws Exception {
        performGetRequest(TASKS_URL)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error")
                        .value(UNAUTHORIZED_ERROR));
    }
}
