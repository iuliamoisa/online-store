package com.iulia.store.mappers;

import com.iulia.store.dtos.UserDto;
import com.iulia.store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
}
