package io.github.danielreker.onlinelearningserver.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestSubmissionRequestDto {
    @NotEmpty(message = "Submission must contain at least one answer")
    @Valid
    private List<UserAnswerDto> answers;
}