package io.github.danielreker.onlinelearningserver.model.dto;

import jakarta.validation.Valid;
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
public class TestDto {
    @NotEmpty(message = "Test must have at least one question")
    @Valid
    private List<QuestionDto> questions;

    @NotEmpty(message = "Test must have a title")
    @Size(max = 255, message = "Text title must be less than 255 characters")
    private String title;
}