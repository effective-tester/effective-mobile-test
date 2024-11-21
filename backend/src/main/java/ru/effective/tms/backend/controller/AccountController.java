package ru.effective.tms.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective.tms.backend.annotation.security.IsAdminOrUser;
import ru.effective.tms.backend.dto.response.UserDto;
import ru.effective.tms.backend.service.AccountService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/me")
    @IsAdminOrUser
    public ResponseEntity<UserDto> me(Authentication authentication) {
        UserDto user = accountService.me(authentication);
        return ResponseEntity.ok(user);
    }
}
