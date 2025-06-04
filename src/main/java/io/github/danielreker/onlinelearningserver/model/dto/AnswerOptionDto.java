package io.github.danielreker.onlinelearningserver.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerOptionDto {
    @NotBlank(message = "Answer option text cannot be blank")
    @Size(max = 500, message = "Answer option text must be less than 500 characters")
    private String text;

    @NotNull(message = "isCorrect flag must be provided")
    private Boolean isCorrect;
}