package com.rubinho.teethshop.controllers;


import com.rubinho.teethshop.dto.CredentialsDto;
import com.rubinho.teethshop.dto.SignUpDto;
import com.rubinho.teethshop.dto.UserDto;
import com.rubinho.teethshop.jwt.UserAuthProvider;
import com.rubinho.teethshop.model.Role;
import com.rubinho.teethshop.services.MailService;
import com.rubinho.teethshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("*")
public class AuthController {
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;
    private final MailService mailService;


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(SignUpDto signUpDto) {
        String code = userService.getCode();


        UserDto user = userService.register(signUpDto, code);

        user.setToken(userAuthProvider.createToken(user.getLogin()));
        user.setRole(Role.UNVERIFIED);

        //TODO давать ссылку на фронт
        mailService.sendActivation(user.getLogin(), "Подтверждение аккаунта", userService.getUniqueUrlForActivation(code));

        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<UserDto> login(@PathVariable String code) {
        UserDto userDto = userService.activateUser(code);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(CredentialsDto credentialsDto) {
        UserDto user = userService.login(credentialsDto);

        user.setToken(userAuthProvider.createToken(user.getLogin()));
//        user.setRole(Role.USER);

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
}
