package ru.effective.tms.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.tms.backend.annotation.log.LogExecution;
import ru.effective.tms.backend.annotation.security.IsAdmin;
import ru.effective.tms.backend.annotation.security.IsAdminOrUser;
import ru.effective.tms.backend.annotation.security.IsAdminOrUserAssignee;
import ru.effective.tms.backend.dto.param.TaskQueryParams;
import ru.effective.tms.backend.dto.request.task.CreateTaskRequest;
import ru.effective.tms.backend.dto.request.task.TaskRequest;
import ru.effective.tms.backend.dto.request.task.UpdateTaskRequest;
import ru.effective.tms.backend.dto.response.TaskDto;
import ru.effective.tms.backend.exception.task.TaskNotFoundException;
import ru.effective.tms.backend.factory.TaskProcessorFactory;
import ru.effective.tms.backend.filter.task.TaskFilter;
import ru.effective.tms.backend.mapper.TaskMapper;
import ru.effective.tms.backend.mapper.TaskQueryMapper;
import ru.effective.tms.backend.model.Task;
import ru.effective.tms.backend.model.User;
import ru.effective.tms.backend.repository.TaskRepository;
import ru.effective.tms.backend.util.UserUtil;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskMapper taskMapper;

    private final TaskQueryMapper paramsMapper;

    private final TaskRepository taskRepository;

    private final TaskProcessorFactory taskProcessorFactory;

    private final UserUtil userUtil;

    @Transactional
    @LogExecution
    @IsAdmin
    public TaskDto createTask(CreateTaskRequest request) {
        Task task = taskMapper.toModel(request);
        setAuthorAndAssignee(task, request);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @Transactional
    @LogExecution
    @IsAdminOrUserAssignee
    public TaskDto getTask(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.toDto(task);
    }

    @Transactional
    @LogExecution
    @IsAdminOrUserAssignee
    public TaskDto updateTask(UUID id, UpdateTaskRequest request) {
        User user = userUtil.getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskProcessorFactory.getProcessorFor(user)
                .update(request, task);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    @Transactional
    @LogExecution
    @IsAdmin
    public void deleteTask(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    @Transactional(readOnly = true)
    @LogExecution
    @IsAdminOrUser
    public Page<TaskDto> getAllTasks(TaskQueryParams params) {
        User user = userUtil.getCurrentUser();
        TaskFilter filter = paramsMapper.toFilter(params);
        Pageable pageable = paramsMapper.toPageable(params);
        return taskProcessorFactory.getProcessorFor(user)
                .getAll(filter, pageable)
                .map(taskMapper::toDto);
    }

    private void setAuthorAndAssignee(Task task, TaskRequest request) {
        User author = userUtil.findUserOrElseCurrent(request.getAuthorId());
        User assignee = userUtil.findUserOrElseNothing(request.getAssigneeId());
        task.setAuthor(author);
        task.setAssignee(assignee);
    }
}

