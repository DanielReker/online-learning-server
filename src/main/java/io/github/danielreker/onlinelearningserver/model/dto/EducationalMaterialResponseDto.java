package io.github.danielreker.onlinelearningserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationalMaterialResponseDto {
    private Long id;
    private String title;
    private String topic;
    private Integer readingTimeMinutes;
    private String authorUsername;
    private List<TextBlockResponseDto> textBlocks;
    private TestResponseDto test;
    private Instant createdAt;
    private Instant updatedAt;
}