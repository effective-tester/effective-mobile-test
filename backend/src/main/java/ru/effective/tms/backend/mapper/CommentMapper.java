package ru.effective.tms.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.effective.tms.backend.dto.request.comment.CreateCommentRequest;
import ru.effective.tms.backend.dto.response.CommentDto;
import ru.effective.tms.backend.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "author.id", target = "authorId")
    CommentDto toDto(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Comment toModel(CreateCommentRequest dto);
}
