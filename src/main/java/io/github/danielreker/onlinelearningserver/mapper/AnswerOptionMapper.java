package io.github.danielreker.onlinelearningserver.mapper;

import io.github.danielreker.onlinelearningserver.model.AnswerOption;
import io.github.danielreker.onlinelearningserver.model.dto.AnswerOptionDto;
import io.github.danielreker.onlinelearningserver.model.dto.AnswerOptionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerOptionMapper {

    AnswerOption toAnswerOption(AnswerOptionDto dto);

    AnswerOptionResponseDto toAnswerOptionResponseDto(AnswerOption entity);

    @Named("answerOptionToResponseDtoList")
    List<AnswerOptionResponseDto> toAnswerOptionResponseDtoList(List<AnswerOption> entityList);

    List<AnswerOption> toAnswerOptionList(List<AnswerOptionDto> dtoList);
}