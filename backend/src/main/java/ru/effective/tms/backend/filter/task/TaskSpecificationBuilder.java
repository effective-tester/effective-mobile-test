package ru.effective.tms.backend.filter.task;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import ru.effective.tms.backend.enum_.filter.FilterOperator;
import ru.effective.tms.backend.enum_.filter.JoinField;
import ru.effective.tms.backend.enum_.filter.FilterParameter;
import ru.effective.tms.backend.model.Task;
import ru.effective.tms.backend.model.User;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TaskSpecificationBuilder {

    private Specification<Task> specification = Specification.where(null);

    private FilterOperator operator = FilterOperator.AND;

    private final Map<FilterParameter, UUID> userFilters =
            new EnumMap<>(FilterParameter.class);

    public TaskSpecificationBuilder withAuthor(UUID authorId) {
        Optional.ofNullable(authorId)
                .ifPresent(id -> userFilters.put(FilterParameter.AUTHOR, id));
        return this;
    }

    public TaskSpecificationBuilder withAssignee(UUID assigneeId) {
        Optional.ofNullable(assigneeId)
                .ifPresent(id -> userFilters.put(FilterParameter.ASSIGNEE, id));
        return this;
    }

    public TaskSpecificationBuilder withOperator(FilterOperator operator) {
        this.operator = Optional.ofNullable(operator)
                .orElse(FilterOperator.AND);
        return this;
    }

    public TaskSpecificationBuilder withCurrentUser(User currentUser) {
        Optional.ofNullable(currentUser)
                .map(User::getId)
                .ifPresent(id -> userFilters.put(FilterParameter.ASSIGNEE, id));
        return this;
    }

    public Specification<Task> build() {
        for (var entry : userFilters.entrySet()) {
            var spec = hasUser(entry.getValue(), entry.getKey());
            specification = combine(specification, spec, operator);
        }
        return specification;
    }

    private Specification<Task> hasUser(UUID userId, FilterParameter parameter) {
        return Optional.ofNullable(userId)
                .map(id -> (Specification<Task>) (root, query, criteriaBuilder) -> {
                    var userJoin = root.join(parameter.value(), JoinType.INNER);
                    return criteriaBuilder.equal(userJoin.get(JoinField.ID.value()), id);
                })
                .orElse((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }

    private Specification<Task> combine(
            Specification<Task> spec1,
            Specification<Task> spec2,
            FilterOperator operator
    ) {
        return switch (operator) {
            case OR -> spec1.or(spec2);
            case AND, DEFAULT -> spec1.and(spec2);
        };
    }

    public static TaskSpecificationBuilder builder() {
        return new TaskSpecificationBuilder();
    }
}

