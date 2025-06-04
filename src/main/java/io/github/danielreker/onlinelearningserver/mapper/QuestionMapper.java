package io.github.danielreker.onlinelearningserver.mapper;

import io.github.danielreker.onlinelearningserver.model.Question;
import io.github.danielreker.onlinelearningserver.model.dto.QuestionDto;
import io.github.danielreker.onlinelearningserver.model.dto.QuestionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AnswerOptionMapper.class})
public interface QuestionMapper {

    Question toQuestion(QuestionDto dto);

    @Mapping(target = "answerOptions", qualifiedByName = "answerOptionToResponseDtoList")
    QuestionResponseDto toQuestionResponseDto(Question entity);

    @Named("questionToResponseDtoList")
    List<QuestionResponseDto> toQuestionResponseDtoList(List<Question> entityList);

    List<Question> toQuestionList(List<QuestionDto> dtoList);
}