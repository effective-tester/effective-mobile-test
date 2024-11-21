package ru.effective.tms.backend.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.effective.tms.backend.exception.TMSRuntimeException;
import ru.effective.tms.backend.util.AspectLogger;

@Aspect
@Component
@RequiredArgsConstructor
public class LogExecutionAspect {

    private final AspectLogger aspectLogger;

    @Pointcut("@annotation(ru.effective.tms.backend.annotation.log.LogExecution)")
    public void logExecutionMethods() {
    }

    @Before("logExecutionMethods()")
    public void logStart(JoinPoint joinPoint) {
        aspectLogger.info(joinPoint, "Executing method with arguments:", joinPoint.getArgs());
    }

    @AfterReturning(
            value = "logExecutionMethods()",
            returning = "result"
    )
    public void logSuccess(JoinPoint joinPoint, Object result) {
        aspectLogger.info(joinPoint, "Method executed successfully. Returned:", result);
    }

    @AfterThrowing(
            value = "logExecutionMethods()",
            throwing = "exception"
    )
    public void logFailure(JoinPoint joinPoint, Throwable exception) {
        aspectLogger.warn(
                joinPoint,
                "Method execution failed. Exception:",
                exception.getMessage() + "-" + exception.getClass());
    }
}


