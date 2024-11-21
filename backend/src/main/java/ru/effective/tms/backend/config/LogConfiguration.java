package ru.effective.tms.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
@Slf4j
public class LogConfiguration {

    @Bean
    public Map<LogLevel, Consumer<String>> logImplementations() {
        Map<LogLevel, Consumer<String>> logImplementations = new EnumMap<>(LogLevel.class);
        logImplementations.put(LogLevel.INFO, log::info);
        logImplementations.put(LogLevel.WARN, log::warn);
        logImplementations.put(LogLevel.ERROR, log::error);
        logImplementations.put(LogLevel.DEBUG, log::debug);
        return logImplementations;
    }
}
