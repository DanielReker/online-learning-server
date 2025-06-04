package io.github.danielreker.onlinelearningserver.mapper;

import io.github.danielreker.onlinelearningserver.model.TextBlock;
import io.github.danielreker.onlinelearningserver.model.dto.TextBlockDto;
import io.github.danielreker.onlinelearningserver.model.dto.TextBlockResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TextBlockMapper {

    TextBlock toTextBlock(TextBlockDto dto);

    TextBlockResponseDto toTextBlockResponseDto(TextBlock entity);

    List<TextBlock> toTextBlockList(List<TextBlockDto> dtoList);

    @Named("textBlockToResponseDtoList")
    List<TextBlockResponseDto> toTextBlockResponseDtoList(List<TextBlock> entityList);
}