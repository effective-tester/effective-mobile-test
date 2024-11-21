package ru.effective.tms.backend.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.effective.tms.backend.enum_.security.UserRole;
import ru.effective.tms.backend.model.Role;
import ru.effective.tms.backend.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EnumTablesInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        List<UserRole> defaultRoles = Arrays.asList(UserRole.ADMIN, UserRole.USER);
        for (UserRole roleName : defaultRoles) {
            roleRepository.findByName(roleName)
                    .orElseGet(() ->
                            roleRepository.save(Role.builder().name(roleName).build()));
        }
    }
}
