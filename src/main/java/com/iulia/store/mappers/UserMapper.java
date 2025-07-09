package com.iulia.store.mappers;

import com.iulia.store.dtos.RegisterUserDto;
import com.iulia.store.dtos.UpdateUserDto;
import com.iulia.store.dtos.UserDto;
import com.iulia.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user); // toDto
    User userDtoToUser(RegisterUserDto registerUserDto); // fromDto = toEntity
    void updateUserFromDto(UpdateUserDto updateUserDto, @MappingTarget User user); // fromDto to existing entity
}
