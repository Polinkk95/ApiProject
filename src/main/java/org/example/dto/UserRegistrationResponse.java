package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.Role;

@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Role role = Role.CLIENT;
}
