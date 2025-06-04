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
public class EducationalMaterialRequestDto {
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;

    @NotBlank(message = "Topic cannot be blank")
    @Size(max = 100, message = "Topic must be less than 100 characters")
    private String topic;

    @NotEmpty(message = "Educational material must have at least one text block")
    @Valid
    private List<TextBlockDto> textBlocks;

    @Valid
    private TestDto test;
}