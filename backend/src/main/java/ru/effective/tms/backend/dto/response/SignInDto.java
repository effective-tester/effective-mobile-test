package ru.effective.tms.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

    @JsonProperty("tokenType")
    private final String tokenType = "Bearer";

    private String accessToken;
}
