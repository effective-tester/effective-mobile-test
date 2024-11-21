package ru.effective.tms.backend.filter.task;

import org.springframework.data.jpa.domain.Specification;
import ru.effective.tms.backend.model.Task;
import ru.effective.tms.backend.model.User;

public class TaskSpecification {

    public static Specification<Task> createUserSpecification(User currentUser, TaskFilter filter) {
        return TaskSpecificationBuilder.builder()
                .withAuthor(filter.getAuthorId())
                .withAssignee(filter.getAssigneeId())
                .withOperator(filter.getOperator())
                .withCurrentUser(currentUser)
                .build();
    }

    public static Specification<Task> createUserSpecification(TaskFilter filter) {
        return createUserSpecification(null, filter);
    }
}

