package ru.effective.tms.backend.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogHandlerAspect {

    @Pointcut("@annotation(ru.effective.tms.backend.annotation.log.LogHandler)")
    public void logHandlerMethods() {
    }

    @Before("logHandlerMethods()")
    public void logBeforeHandlerMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Handling an exception in method: {}. Exception: {}",
                methodName, joinPoint.getArgs());
    }
}
