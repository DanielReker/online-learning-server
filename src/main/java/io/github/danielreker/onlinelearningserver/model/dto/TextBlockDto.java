package io.github.danielreker.onlinelearningserver.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextBlockDto {
    @Size(max = 255, message = "Text block title must be less than 255 characters")
    private String title;

    @NotBlank(message = "Text block content cannot be blank")
    private String content;
}