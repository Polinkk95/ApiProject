package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserLoginRequest;
import org.example.dto.UserRegistrationRequest;
import org.example.dto.UserRegistrationResponse;
import org.example.service.EntranceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/entrance")
public class EntranceController {

    private final EntranceService entranceService;

    @PostMapping("/login")
    public UserRegistrationResponse login(
            @RequestBody UserLoginRequest userLogin) {
        return entranceService.login(userLogin);
    }

    @PostMapping("/register")
    public UserRegistrationResponse register(
            @RequestBody UserRegistrationRequest userRegistration) {
        return entranceService.register(userRegistration);
    }

    @PutMapping("/update-user/{userId}")
    public UserRegistrationResponse updateUser(
            @PathVariable Long userId,
            @RequestBody UserRegistrationRequest userRegistration){
        return entranceService.updateUser(userId,userRegistration);
    }

}
