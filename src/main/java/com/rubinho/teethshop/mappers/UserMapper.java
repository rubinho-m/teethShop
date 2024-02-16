package com.rubinho.teethshop.mappers;


import com.rubinho.teethshop.dto.SignUpDto;
import com.rubinho.teethshop.dto.UserDto;
import com.rubinho.teethshop.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto userDto);
}
