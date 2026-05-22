package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserRegistrationResponse;
import org.example.enums.Role;
import org.example.service.UserRoleService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PatchMapping("/{userId}/role")
    public UserRegistrationResponse changeRole(
            @PathVariable Long userId,
            @RequestParam Role role) {
        return userRoleService.changeRole(userId, role);
    }


}
