package io.github.danielreker.onlinelearningserver.mapper;

import io.github.danielreker.onlinelearningserver.model.Test;
import io.github.danielreker.onlinelearningserver.model.dto.TestDto;
import io.github.danielreker.onlinelearningserver.model.dto.TestResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface TestMapper {

    Test toTest(TestDto dto);

    @Named("testToResponseDto")
    @Mapping(target = "questions", qualifiedByName = "questionToResponseDtoList")
    TestResponseDto toTestResponseDto(Test entity);
}