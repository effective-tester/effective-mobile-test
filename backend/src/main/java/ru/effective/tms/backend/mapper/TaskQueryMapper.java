package ru.effective.tms.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.effective.tms.backend.dto.param.TaskQueryParams;
import ru.effective.tms.backend.enum_.filter.FilterOperator;
import ru.effective.tms.backend.enum_.filter.SortDir;
import ru.effective.tms.backend.filter.task.TaskFilter;

@Component
@RequiredArgsConstructor
public class TaskQueryMapper {

    public TaskFilter toFilter(TaskQueryParams queryParams) {
        var operator = FilterOperator.fromString(queryParams.getOperator());
        return TaskFilter.builder()
                .authorId(queryParams.getAuthorId())
                .assigneeId(queryParams.getAssigneeId())
                .operator(operator)
                .build();
    }

    public Pageable toPageable(TaskQueryParams queryParams) {
        Sort sort = queryParams.getSortDir().equalsIgnoreCase(SortDir.DESC.value())
                ? Sort.by(queryParams.getSortBy()).ascending()
                : Sort.by(queryParams.getSortBy()).descending();
        return PageRequest.of(queryParams.getPage(), queryParams.getSize(), sort);
    }
}
