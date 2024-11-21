package ru.effective.tms.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.tms.backend.annotation.log.LogExecution;
import ru.effective.tms.backend.dto.response.UserDto;
import ru.effective.tms.backend.exception.task.UserNotFoundException;
import ru.effective.tms.backend.mapper.UserMapper;
import ru.effective.tms.backend.model.User;
import ru.effective.tms.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Transactional
    @LogExecution
    public UserDto me(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }
}
