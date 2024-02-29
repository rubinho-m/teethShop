package com.rubinho.teethshop.dto;

import com.rubinho.teethshop.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String secondName;
    private String login;
    private String token;
    private Role role;
    private String code;

}
