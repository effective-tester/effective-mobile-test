package ru.effective.tms.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpDto {

    @JsonProperty("message")
    private final String message = "User registered successfully";
}
