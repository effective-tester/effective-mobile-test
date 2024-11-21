package ru.effective.tms.backend.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.effective.tms.backend.validation.CreateGroup;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest extends CommentRequest {

    @NotBlank(groups = CreateGroup.class)
    private String content;
}