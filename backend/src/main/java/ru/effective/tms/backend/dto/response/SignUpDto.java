package ru.effective.tms.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignUpDto {

    @JsonProperty("message")
    private final String message = "User registered successfully";
}
