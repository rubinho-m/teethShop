package com.rubinho.teethshop.services;

import com.rubinho.teethshop.dto.CredentialsDto;
import com.rubinho.teethshop.dto.SignUpDto;
import com.rubinho.teethshop.dto.UserDto;
import com.rubinho.teethshop.model.Role;
import com.rubinho.teethshop.model.User;
import com.rubinho.teethshop.exceptions.AppException;
import com.rubinho.teethshop.mappers.UserMapper;
import com.rubinho.teethshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }

        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);

    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());
        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(user);

    }

    private static List<GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        return authorities;
    }

    public UserDetails getUserDetails(String username){
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getAuthorities(roles));


    }
}
