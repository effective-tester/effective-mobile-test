package ru.effective.tms.backend.util;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class AspectLogger {

    private static final String EMPTY_STRING = "";

    private final Map<LogLevel, Consumer<String>> logImplementations;

    public void info(JoinPoint joinPoint, String message) {
        info(joinPoint, message, (Object[]) null);
    }

    public void info(JoinPoint joinPoint, String message, Object... params) {
        log(LogLevel.INFO, joinPoint, message, params);
    }

    public void warn(JoinPoint joinPoint, String message, Object... params) {
        log(LogLevel.WARN, joinPoint, message, params);
    }

    public void log(LogLevel level, JoinPoint joinPoint, String message, Object... params) {
        String methodName = joinPoint.getSignature().toShortString();
        String formattedParams = formatParams(params);
        String logMessage = String
                .format("[%s] %s %s", methodName, message, formattedParams)
                .trim();
        logImplementations.getOrDefault(level, logImplementations.get(LogLevel.INFO))
                .accept(logMessage);
    }

    private String formatParams(Object... params) {
        return Optional.ofNullable(params)
                .map(Arrays::toString)
                .orElse(EMPTY_STRING);
    }
}
