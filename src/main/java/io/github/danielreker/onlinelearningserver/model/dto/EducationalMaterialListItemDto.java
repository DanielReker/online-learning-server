package io.github.danielreker.onlinelearningserver.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class EducationalMaterialListItemDto {
    private Long id;
    private String title;
    private String topic;
    private Integer readingTimeMinutes;
    private String authorUsername;
    private Instant createdAt;
}