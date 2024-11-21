package ru.effective.tms.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.tms.backend.exception.task.UserNotFoundException;
import ru.effective.tms.backend.model.User;
import ru.effective.tms.backend.repository.UserRepository;
import ru.effective.tms.backend.security.UserPrincipal;

@Service
@RequiredArgsConstructor
public class TaskUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with email: " + email));
        return UserPrincipal.create(user);
    }
}
