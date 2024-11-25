package ru.effective.tms.backend.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.effective.tms.backend.validation.CreateGroup;

@Setter
@Getter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest extends CommentRequest {

    @NotBlank(groups = CreateGroup.class)
    private String content;
}