package org.example.dto.mapping;

import org.example.dto.UserRegistrationRequest;
import org.example.dto.UserRegistrationResponse;
import org.example.model.UserRegistration;
import org.example.repository.UserDataRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = UserDataRepository.class)
public abstract class UserDataMapping {

    @Autowired
    UserDataRepository userDataRepository;

    public abstract UserRegistration toEntity(UserRegistrationRequest allUserData);

    public abstract UserRegistrationResponse toResponseUserData(UserRegistration registration);
}
