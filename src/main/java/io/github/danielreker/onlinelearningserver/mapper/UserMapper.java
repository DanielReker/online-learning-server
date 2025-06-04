package io.github.danielreker.onlinelearningserver.mapper;

import io.github.danielreker.onlinelearningserver.model.User;
import io.github.danielreker.onlinelearningserver.model.dto.auth.RegistrationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toUser(RegistrationRequestDto registrationRequestDto);
}