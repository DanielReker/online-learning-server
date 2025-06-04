package io.github.danielreker.onlinelearningserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextBlockResponseDto {
    private Long id;
    private String title;
    private String content;
}