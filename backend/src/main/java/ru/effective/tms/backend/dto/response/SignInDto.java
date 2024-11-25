package ru.effective.tms.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

    @JsonProperty("tokenType")
    private final String tokenType = "Bearer";

    private String accessToken;
}
