package io.github.danielreker.onlinelearningserver.mapper;

import io.github.danielreker.onlinelearningserver.model.EducationalMaterial;
import io.github.danielreker.onlinelearningserver.model.User;
import io.github.danielreker.onlinelearningserver.model.dto.EducationalMaterialListItemDto;
import io.github.danielreker.onlinelearningserver.model.dto.EducationalMaterialRequestDto;
import io.github.danielreker.onlinelearningserver.model.dto.EducationalMaterialResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TextBlockMapper.class, TestMapper.class})
public interface EducationalMaterialMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", source = "author")
    @Mapping(target = "textBlocks", ignore = true)
    @Mapping(target = "test", source = "requestDto.test")
    EducationalMaterial toEducationalMaterial(EducationalMaterialRequestDto requestDto, User author);

    @Mapping(target = "authorUsername", source = "author.username")
    @Mapping(target = "textBlocks", qualifiedByName = "textBlockToResponseDtoList")
    @Mapping(target = "test", qualifiedByName = "testToResponseDto")
    EducationalMaterialResponseDto toEducationalMaterialResponseDto(EducationalMaterial material);

    @Mapping(target = "authorUsername", source = "author.username")
    EducationalMaterialListItemDto toEducationalMaterialListItemDto(EducationalMaterial material);

    void updateEducationalMaterialFromDto(EducationalMaterialRequestDto dto, @MappingTarget EducationalMaterial entity);
}