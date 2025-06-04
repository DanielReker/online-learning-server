package io.github.danielreker.onlinelearningserver.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResultDto {
    private Long materialId;
    private double scorePercentage;
    private int totalQuestions;
    private int correctAnswersCount;
}