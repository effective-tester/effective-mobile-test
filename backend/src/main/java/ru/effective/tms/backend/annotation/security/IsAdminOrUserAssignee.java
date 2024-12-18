package ru.effective.tms.backend.annotation.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("""
    hasAuthority('ADMIN') or (
        hasAuthority('USER') and 
        @taskSecurity.isAssignee(authentication, #taskId)
    )
""")
public @interface IsAdminOrUserAssignee {
}

