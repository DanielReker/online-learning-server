package io.github.danielreker.onlinelearningserver.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
}