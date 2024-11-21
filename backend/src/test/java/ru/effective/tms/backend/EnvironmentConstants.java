package ru.effective.tms.backend;

public class EnvironmentConstants {
    
    protected static final String REGISTER_URL = "/auth/signup";

    protected static final String LOGIN_URL = "/auth/signin";

    protected static final String ME_URL = "/me";

    protected static final String TASKS_URL = "/tasks";

    protected static final String TASK_URL = "/tasks/{taskId}";

    protected static final String COMMENTS_URL = "/tasks/{taskId}/comments";

    protected static final String ACCESS_DENIED_ERROR = "Access is denied";

    protected static final String UNAUTHORIZED_ERROR = "Unauthorized";

    protected static final String TASK_NOT_FOUND_ERROR = "Task not found with id: ";

    protected static final String SUCCESS_MESSAGE = "User registered successfully";

    protected static final String EMAIL_ALREADY_IN_USE_ERROR = "Email already in use";

    protected static final String BAD_CREDENTIALS_ERROR = "Bad credentials";

    public static final String USER_USERNAME = "username";

    public static final String USER_EMAIL = "user@example.com";

    public static final String USER_PASSWORD = "?User123";

    public static final String ADMIN_USERNAME = "adminname";

    public static final String ADMIN_EMAIL = "admin@example.com";

    public static final String ADMIN_PASSWORD = "?Admin123";

    public static final String INVALID_PASSWORD = "wrongpassword";

    protected static final String TITLE = "Title";

    protected static final String UPDATED_TITLE = "Updated Title";

    protected static final String CONTENT = "Comment content";

    protected static final String AUTHORIZATION_HEADER = "Authorization";

    protected static final String TOKEN_TYPE = "Bearer";

    protected static final String TOKEN_TYPE_FIELD_NAME = "tokenType";

    protected static final String TOKEN_ACCESS_TOKEN_FIELD_NAME = "accessToken";

    protected static final String TASK_ID_FIELD_NAME = "id";

    protected static final String USER_ID_FIELD_NAME = "id";

    protected static final int PAGE_SIZE = 10;
}
