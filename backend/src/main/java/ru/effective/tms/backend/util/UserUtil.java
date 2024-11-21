package ru.effective.tms.backend.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.effective.tms.backend.exception.task.UserNotFoundException;
import ru.effective.tms.backend.model.User;
import ru.effective.tms.backend.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));
    }

    public User findUserOrElseCurrent(UUID userId) {
        return findUserOrDefault(userId, this::getCurrentUser);
    }

    public User findUserOrElseNothing(UUID userId) {
        return findUserOrDefault(userId, () -> null);
    }

    private User findUserOrDefault(UUID userId, Supplier<User> defaultUserSupplier) {
        return Optional.ofNullable(userId)
                .map(this::findUserOrThrow)
                .orElseGet(defaultUserSupplier);
    }

    private User findUserOrThrow(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
