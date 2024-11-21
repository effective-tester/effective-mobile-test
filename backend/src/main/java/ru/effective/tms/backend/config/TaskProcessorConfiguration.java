package ru.effective.tms.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.effective.tms.backend.enum_.security.UserRole;
import ru.effective.tms.backend.factory.task.AdminTaskProcessor;
import ru.effective.tms.backend.factory.task.TaskProcessor;
import ru.effective.tms.backend.factory.task.UserTaskProcessor;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class TaskProcessorConfiguration {

    @Bean
    public Map<UserRole, TaskProcessor> getTaskProcessors(
            AdminTaskProcessor adminTaskProcessor,
            UserTaskProcessor userTaskProcessor
    ) {
        Map<UserRole, TaskProcessor> taskProcessors = new EnumMap<>(UserRole.class);
        taskProcessors.put(UserRole.ADMIN, adminTaskProcessor);
        taskProcessors.put(UserRole.USER, userTaskProcessor);
        return taskProcessors;
    }
}
