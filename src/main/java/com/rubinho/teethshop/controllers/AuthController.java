package com.rubinho.teethshop.controllers;


import com.rubinho.teethshop.dto.CredentialsDto;
import com.rubinho.teethshop.dto.SignUpDto;
import com.rubinho.teethshop.dto.UserDto;
import com.rubinho.teethshop.jwt.UserAuthProvider;
import com.rubinho.teethshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("*")
public class AuthController {
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(SignUpDto signUpDto){
        UserDto user = userService.register(signUpDto);

        user.setToken(userAuthProvider.createToken(user.getLogin()));

        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);

    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(CredentialsDto credentialsDto){
        UserDto user = userService.login(credentialsDto);

        user.setToken(userAuthProvider.createToken(user.getLogin()));

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
}
