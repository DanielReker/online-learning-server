package io.github.danielreker.onlinelearningserver.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAnswerDto {
    @NotNull(message = "Question ID cannot be null")
    private Long questionId;

    @NotNull(message = "Selected answer option ID cannot be null")
    private Long selectedAnswerOptionId;
}