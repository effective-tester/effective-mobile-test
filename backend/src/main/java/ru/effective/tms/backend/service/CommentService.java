package ru.effective.tms.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.tms.backend.annotation.log.LogExecution;
import ru.effective.tms.backend.dto.request.comment.CreateCommentRequest;
import ru.effective.tms.backend.dto.response.CommentDto;
import ru.effective.tms.backend.exception.task.TaskNotFoundException;
import ru.effective.tms.backend.mapper.CommentMapper;
import ru.effective.tms.backend.model.Comment;
import ru.effective.tms.backend.model.Task;
import ru.effective.tms.backend.model.User;
import ru.effective.tms.backend.repository.CommentRepository;
import ru.effective.tms.backend.repository.TaskRepository;
import ru.effective.tms.backend.util.UserUtil;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    private final TaskRepository taskRepository;

    private final CommentRepository commentRepository;

    private final UserUtil userUtil;

    @Transactional
    @LogExecution
    public CommentDto addComment(UUID taskId, CreateCommentRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        Comment comment = commentMapper.toModel(request);
        setTaskAndAuthor(comment, task);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    private void setTaskAndAuthor(Comment comment, Task task) {
        User author = userUtil.getCurrentUser();
        comment.setTask(task);
        comment.setAuthor(author);
    }
}
