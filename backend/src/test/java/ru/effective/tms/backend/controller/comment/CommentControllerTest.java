package ru.effective.tms.backend.controller.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import ru.effective.tms.backend.TestEnvironment;
import ru.effective.tms.backend.dto.request.comment.CreateCommentRequest;
import ru.effective.tms.backend.enum_.security.UserRole;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends TestEnvironment {

    private CreateCommentRequest createCommentRequest;

    @BeforeEach
    public void setup() throws Exception {
        registerAll();
        createCommentRequest = createCommentRequest();
    }

    @Test
    public void addComment_shouldReturnCreated_whenAdminToUserRequest() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.USER);
        performPostRequestAs(COMMENTS_URL, taskId, createCommentRequest, UserRole.ADMIN)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content")
                        .value(createCommentRequest.getContent()));
    }

    @Test
    public void addComment_shouldReturnCreated_whenUserIsAssignee() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.USER);
        performPostRequestAs(COMMENTS_URL, taskId, createCommentRequest, UserRole.USER)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content")
                        .value(createCommentRequest.getContent()));
    }

    @Test
    public void addComment_shouldReturnForbidden_whenUserIsNotAssignee() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.ADMIN);
        performPostRequestAs(COMMENTS_URL, taskId, createCommentRequest, UserRole.USER)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error")
                        .value(ACCESS_DENIED_ERROR));
    }

    @Test
    public void addComment_shouldReturnUnauthorized_whenNotAuthenticated() throws Exception {
        var taskId = taskIdAssigneeWith(UserRole.ADMIN);
        performPostRequest(COMMENTS_URL, taskId, createCommentRequest)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error")
                        .value(UNAUTHORIZED_ERROR));
    }
}
