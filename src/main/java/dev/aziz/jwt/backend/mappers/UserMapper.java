package dev.aziz.jwt.backend.mappers;

import dev.aziz.jwt.backend.dtos.SignUpDto;
import dev.aziz.jwt.backend.dtos.UserDto;
import dev.aziz.jwt.backend.entites.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
