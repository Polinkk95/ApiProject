package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserLoginRequest;
import org.example.dto.UserRegistrationRequest;
import org.example.dto.UserRegistrationResponse;
import org.example.dto.mapping.UserDataMapping;
import org.example.exception.UserNotFoundException;
import org.example.exception.WrongPasswordException;
import org.example.model.UserRegistration;
import org.example.repository.UserDataRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntranceService {

    private final UserDataRepository userDataRepository;

    private final UserDataMapping userDataMapping;


    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserRegistrationResponse login(UserLoginRequest userLogin) {
        String email = userLogin.getEmail();
        UserRegistration userData = userDataRepository.findByEmail(email);
        if (userData == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        String passwordFromUser = userLogin.getPassword();
        String passwordFromDatabase = userData.getPassword();
        if (!passwordEncoder.matches(passwordFromUser, passwordFromDatabase)) {
            throw new WrongPasswordException("Wrong password");
        }
        return userDataMapping.toResponseUserData(userData);
    }

    @Transactional
    public UserRegistrationResponse register(UserRegistrationRequest userRegistration) {
        String email = userRegistration.getEmail();
        if (userDataRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("This email is already busy");
        }
        String hashPassword = passwordEncoder.encode(userRegistration.getPassword());
        UserRegistration newUser = userDataMapping.toEntity(userRegistration);
        newUser.setPassword(hashPassword);
        userDataRepository.save(newUser);
        return userDataMapping.toResponseUserData(newUser);
    }

    @Transactional
    public UserRegistrationResponse updateUser(Long userId,UserRegistrationRequest userRegistration){
        String newFirstName = userRegistration.getFirstName();
        String newLastName = userRegistration.getLastName();
        String newEmail = userRegistration.getEmail();
        String newPassword = userRegistration.getPassword();
        String newPhone = userRegistration.getPhoneNumber();
        UserRegistration currentUser = userDataRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User with id "+userId+" not found"));
        if(!newFirstName.equals(currentUser.getFirstName())){
            currentUser.setFirstName(newFirstName);
        }
        if(!newLastName.equals(currentUser.getLastName())){
            currentUser.setLastName(newLastName);
        }
        if(!newEmail.equals(currentUser.getEmail())){
            if(userDataRepository.existsByEmail(newEmail)){
                throw new IllegalArgumentException("This email is already busy");
            }
            currentUser.setEmail(newEmail);
        }
        if(!newPhone.equals(currentUser.getPhoneNumber())){
            currentUser.setPhoneNumber(newPhone);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            if (!passwordEncoder.matches(newPassword, currentUser.getPassword())) {
                currentUser.setPassword(passwordEncoder.encode(newPassword));
            }
        }
        userDataRepository.save(currentUser);
        return userDataMapping.toResponseUserData(currentUser);
    }
}
