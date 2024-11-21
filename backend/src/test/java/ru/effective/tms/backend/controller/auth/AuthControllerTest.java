package ru.effective.tms.backend.controller.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.effective.tms.backend.TestEnvironment;
import ru.effective.tms.backend.dto.request.auth.RegisterRequest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends TestEnvironment {

    private RegisterRequest registerRequest;

    @BeforeEach
    public void setup() {
        registerRequest = createRegisterRequest();
    }

    @Test
    public void register_shouldReturnCreated_whenValidRequest() throws Exception {
        performPostRequest(REGISTER_URL, registerRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message")
                        .value(SUCCESS_MESSAGE));
    }

    @Test
    public void register_shouldReturnBadRequest_whenEmailAlreadyInUse() throws Exception {
        performPostRequest(REGISTER_URL, registerRequest);
        performPostRequest(REGISTER_URL, registerRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value(EMAIL_ALREADY_IN_USE_ERROR));
    }

    @Test
    public void authenticate_shouldReturnToken_whenValidCredentials() throws Exception {
        performPostRequest(REGISTER_URL, registerRequest);
        var loginRequest = createLoginRequest();
        performPostRequest(LOGIN_URL, loginRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType")
                        .value(TOKEN_TYPE))
                .andExpect(jsonPath("$.accessToken")
                        .isNotEmpty());
    }

    @Test
    public void authenticate_shouldReturnUnauthorized_whenBadCredentials() throws Exception {
        performPostRequest(REGISTER_URL, registerRequest);
        var invalidLoginRequest = createLoginRequestInvalid();
        performPostRequest(LOGIN_URL, invalidLoginRequest)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error")
                        .value(BAD_CREDENTIALS_ERROR));
    }
}



