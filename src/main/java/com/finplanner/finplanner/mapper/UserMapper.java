package com.finplanner.finplanner.mapper;

import com.finplanner.finplanner.dto.auth.UserRegistrationDto;
import com.finplanner.finplanner.dto.user.UserDto;
import com.finplanner.finplanner.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    UserDto toUserDto(User user);

    UserDto toUserDto(UserRegistrationDto userRegistrationDto);

    User toUser(UserRegistrationDto userRegistrationDto);

    User toUser(UserDto userDto);
}
