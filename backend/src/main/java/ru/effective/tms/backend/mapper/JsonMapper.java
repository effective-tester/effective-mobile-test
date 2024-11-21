package ru.effective.tms.backend.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public String createUnauthorizedJson() {
        Map<String, String> errorResponse =
                Map.of("error", "Unauthorized");
        try {
            return mapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
