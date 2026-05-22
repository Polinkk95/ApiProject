package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserRegistrationResponse;
import org.example.dto.mapping.UserDataMapping;
import org.example.enums.Role;
import org.example.model.UserRegistration;
import org.example.repository.UserDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserDataRepository userDataRepository;

    private final UserDataMapping userMapping;

    @Transactional
    public UserRegistrationResponse changeRole(Long userId, Role role){
        UserRegistration user = userDataRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("User with id "+userId+" not found"));
        user.setRole(role);
        userDataRepository.save(user);
        return userMapping.toResponseUserData(user);
    }

}
