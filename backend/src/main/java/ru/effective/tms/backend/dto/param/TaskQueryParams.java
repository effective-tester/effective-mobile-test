package ru.effective.tms.backend.dto.param;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskQueryParams {

    private UUID authorId;

    private UUID assigneeId;

    @Pattern(
            regexp = "AND|OR",
            flags = Pattern.Flag.CASE_INSENSITIVE
    )
    private String operator = "AND";

    private int page = 0;

    private int size = 10;

    private String sortBy = "createdAt";

    private String sortDir = "desc";
}
