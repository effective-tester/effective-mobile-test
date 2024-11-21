package ru.effective.tms.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.tms.backend.annotation.log.LogExecution;
import ru.effective.tms.backend.dto.request.auth.LoginRequest;
import ru.effective.tms.backend.dto.request.auth.RegisterRequest;
import ru.effective.tms.backend.dto.response.SignInDto;
import ru.effective.tms.backend.dto.response.SignUpDto;
import ru.effective.tms.backend.dto.response.UserDto;
import ru.effective.tms.backend.enum_.security.UserRole;
import ru.effective.tms.backend.exception.security.EmailAlreadyInUseException;
import ru.effective.tms.backend.exception.security.RoleNotFoundException;
import ru.effective.tms.backend.exception.task.UserNotFoundException;
import ru.effective.tms.backend.mapper.UserMapper;
import ru.effective.tms.backend.model.Role;
import ru.effective.tms.backend.model.User;
import ru.effective.tms.backend.repository.RoleRepository;
import ru.effective.tms.backend.repository.UserRepository;
import ru.effective.tms.backend.security.JwtTokenProvider;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final UserRole DEFAULT_ROLE = UserRole.USER;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    @Transactional
    @LogExecution
    public SignUpDto register(RegisterRequest registerRequest) {
        validateEmailAvailability(registerRequest.getEmail());
        User user = createAndAssignRoleToUser(registerRequest);
        userRepository.save(user);
        return new SignUpDto();
    }

    @Transactional
    @LogExecution
    public SignInDto authenticate(LoginRequest loginRequest) {
        var authentication = createAuthentication(loginRequest);
        String jwt = tokenProvider.generateToken(authentication);
        return new SignInDto(jwt);
    }

    private Authentication createAuthentication(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
    }

    private void validateEmailAvailability(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException();
        }
    }

    private User createAndAssignRoleToUser(RegisterRequest registerRequest) {
        User user = buildUser(registerRequest);
        Role role = findRole(registerRequest.getRole());
        user.getRoles().add(role);
        return user;
    }

    private User buildUser(RegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
    }

    private Role findRole(UserRole roleName) {
        var roleToFind = Optional.ofNullable(roleName)
                .orElse(DEFAULT_ROLE);
        return roleRepository.findByName(roleToFind)
                .orElseThrow(RoleNotFoundException::new);
    }
}


