package ru.effective.tms.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.effective.tms.backend.dto.request.auth.LoginRequest;
import ru.effective.tms.backend.dto.request.auth.RegisterRequest;
import ru.effective.tms.backend.dto.request.comment.CreateCommentRequest;
import ru.effective.tms.backend.dto.request.task.CreateTaskRequest;
import ru.effective.tms.backend.dto.request.task.UpdateTaskRequest;
import ru.effective.tms.backend.enum_.security.UserRole;
import ru.effective.tms.backend.enum_.task.TaskPriority;
import ru.effective.tms.backend.enum_.task.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TestEnvironment extends EnvironmentConstants {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    protected ResultActions performPostRequest(String url, Object requestData) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestData)));
    }

    protected ResultActions performPostRequest(
            String url, String param, Object requestData
    ) throws Exception {
        return mockMvc.perform(post(url, param)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestData)));
    }

    protected ResultActions performPostRequestAs(
            String url, Object requestData, UserRole role
    ) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestData))
                .header(AUTHORIZATION_HEADER, authenticateAs(role)));
    }

    protected ResultActions performPostRequestAs(
            String url, String param, Object requestData, UserRole role
    ) throws Exception {
        return mockMvc.perform(post(url, param)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestData))
                .header(AUTHORIZATION_HEADER, authenticateAs(role)));
    }

    protected ResultActions performGetRequest(String url) throws Exception {
        return mockMvc.perform(get(url));
    }

    protected ResultActions performGetRequest(String url, String param) throws Exception {
        return mockMvc.perform(get(url, param));
    }

    protected ResultActions performGetRequestAs(String url, UserRole role) throws Exception {
        return mockMvc.perform(get(url)
                .header(AUTHORIZATION_HEADER, authenticateAs(role)));
    }

    protected ResultActions performGetRequestAs(
            String url, String param, UserRole role
    ) throws Exception {
        return mockMvc.perform(get(url, param)
                .header(AUTHORIZATION_HEADER, authenticateAs(role)));
    }

    protected ResultActions performPutRequest(
            String url, String param, Object requestData
    ) throws Exception {
        return mockMvc.perform(put(url, param)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestData)));
    }

    protected ResultActions performPutRequestAs(
            String url, String param, Object requestData, UserRole role
    ) throws Exception {
        return mockMvc.perform(put(url, param)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestData))
                .header(AUTHORIZATION_HEADER, authenticateAs(role)));
    }

    protected ResultActions performDeleteRequest(String url, String param) throws Exception {
        return mockMvc.perform(delete(url, param));
    }

    protected ResultActions performDeleteRequestAs(
            String url, String param, UserRole role
    ) throws Exception {
        return mockMvc.perform(delete(url, param)
                .header(AUTHORIZATION_HEADER, authenticateAs(role)));
    }

    private RegisterRequest createRegisterRequestUser() {
        return createRegisterRequest(
                USER_USERNAME, USER_EMAIL, USER_PASSWORD, UserRole.USER);
    }

    private RegisterRequest createRegisterRequestAdmin() {
        return createRegisterRequest(
                ADMIN_USERNAME, ADMIN_EMAIL, ADMIN_PASSWORD, UserRole.ADMIN);
    }

    private RegisterRequest createRegisterRequestNothing() {
        return createRegisterRequest(
                ADMIN_USERNAME, ADMIN_EMAIL, ADMIN_PASSWORD, null);
    }

    protected RegisterRequest createRegisterRequest(
            String name, String email, String password, UserRole role
    ) {
        return RegisterRequest.builder()
                .username(name)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    protected RegisterRequest createRegisterRequest() {
        return createRegisterRequest(UserRole.USER);
    }

    protected RegisterRequest createRegisterRequest(UserRole role) {
        return switch (role) {
            case null -> createRegisterRequestNothing();
            case UserRole.ADMIN -> createRegisterRequestAdmin();
            case UserRole.USER -> createRegisterRequestUser();
        };
    }

    protected LoginRequest createLoginRequestInvalid() {
        return createLoginRequest(USER_EMAIL, INVALID_PASSWORD);
    }

    protected LoginRequest createLoginRequestUser() {
        return createLoginRequest(USER_EMAIL, USER_PASSWORD);
    }

    protected LoginRequest createLoginRequestAdmin() {
        return createLoginRequest(ADMIN_EMAIL, ADMIN_PASSWORD);
    }

    protected LoginRequest createLoginRequest(String email, String password) {
        return LoginRequest.builder()
                .email(email)
                .password(password)
                .build();
    }

    protected LoginRequest createLoginRequest() {
        return createLoginRequest(UserRole.USER);
    }

    protected LoginRequest createLoginRequest(UserRole role) {
        return switch (role) {
            case UserRole.ADMIN -> createLoginRequestAdmin();
            case UserRole.USER -> createLoginRequestUser();
        };
    }

    protected CreateTaskRequest createTaskRequest() {
        return taskRequestDefaultBuilder().build();
    }

    protected CreateTaskRequest createTaskRequestWithAssignee(String id) {
        return taskRequestDefaultBuilder()
                .assigneeId(UUID.fromString(id))
                .build();
    }

    private CreateTaskRequest.CreateTaskRequestBuilder taskRequestDefaultBuilder() {
        return CreateTaskRequest.builder()
                .title(TITLE)
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.MEDIUM);
    }

    protected CreateCommentRequest createCommentRequest() {
        return CreateCommentRequest.builder()
                .content(CONTENT)
                .build();
    }

    protected UpdateTaskRequest updateTaskRequest() {
        return UpdateTaskRequest.builder()
                .title(UPDATED_TITLE)
                .status(TaskStatus.COMPLETED)
                .priority(TaskPriority.HIGH)
                .build();
    }


    protected void registerAll() throws Exception {
        registerAs(UserRole.USER);
        registerAs(UserRole.ADMIN);
    }

    private void registerAs(UserRole role) throws Exception {
        var registerRequest = createRegisterRequest(role);
        performPostRequest(REGISTER_URL, registerRequest);
    }

    private String authenticateAs(UserRole role) throws Exception {
        var loginRequest = createLoginRequest(role);
        var token = mapper.readTree(
                createTree(performPostRequest(LOGIN_URL, loginRequest)));
        return String.format("%s %s",
                token.get(TOKEN_TYPE_FIELD_NAME).asText(),
                token.get(TOKEN_ACCESS_TOKEN_FIELD_NAME).asText()
        );
    }

    protected JsonNode taskWithAssignee(JsonNode user) throws Exception {
        var id = user.path(USER_ID_FIELD_NAME).asText();
        var request = createTaskRequestWithAssignee(id);
        return requestToUser(request);
    }

    protected List<String> tasksWithAssignee(UserRole role, int tasksNumber) throws Exception {
        List<String> tasks = new ArrayList<>();
        tasksNumber = Math.max(tasksNumber, 0);
        for (int i = 0; i < tasksNumber; i++) {
            tasks.add(taskIdAssigneeWith(role));
        }
        return tasks;
    }

    protected String taskIdAssigneeWith(UserRole role) throws Exception {
        var assignee = getAuthenticatedUser(role);
        var task = taskWithAssignee(assignee);
        return task.get(TASK_ID_FIELD_NAME).asText();
    }

    private JsonNode requestToUser(CreateTaskRequest request) throws Exception {
        var task = createTree(
                performPostRequestAs(TASKS_URL, request, UserRole.ADMIN));
        return mapper.readTree(task);
    }

    protected JsonNode getAuthenticatedUser(UserRole role) throws Exception {
        return mapper.readTree(
                createTree(performGetRequestAs(ME_URL, role)));
    }

    private String createTree(ResultActions resultActions) throws Exception {
        return resultActions
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
