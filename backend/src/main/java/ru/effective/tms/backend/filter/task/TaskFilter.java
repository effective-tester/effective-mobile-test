package ru.effective.tms.backend.filter.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.effective.tms.backend.enum_.filter.FilterOperator;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskFilter {

    private UUID authorId;

    private UUID assigneeId;

    @Builder.Default
    private FilterOperator operator = FilterOperator.AND;
}