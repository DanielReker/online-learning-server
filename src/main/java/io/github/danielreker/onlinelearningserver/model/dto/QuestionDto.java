package io.github.danielreker.onlinelearningserver.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    @NotBlank(message = "Question text cannot be blank")
    @Size(max = 1000, message = "Question text must be less than 1000 characters")
    private String text;

    @NotEmpty(message = "Question must have at least one answer option")
    @Valid
    private List<AnswerOptionDto> answerOptions;
}