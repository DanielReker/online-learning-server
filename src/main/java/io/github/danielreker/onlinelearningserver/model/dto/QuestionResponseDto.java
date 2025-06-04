package io.github.danielreker.onlinelearningserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto {
    private Long id;
    private String text;
    private List<AnswerOptionResponseDto> answerOptions;
    private Integer questionOrder;
}