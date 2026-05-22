package org.example.service;

import org.example.dto.UserRegistrationResponse;
import org.example.dto.mapping.UserDataMapping;
import org.example.enums.Role;
import org.example.model.UserRegistration;
import org.example.repository.UserDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceTest {

    @Mock
    private UserDataRepository userDataRepository;

    @Mock
    private UserDataMapping userMapping;

    @InjectMocks
    private UserRoleService userRoleService;

    private UserRegistration user;
    private UserRegistrationResponse response;

    @BeforeEach
    void setUp() {
        user = new UserRegistration();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPhoneNumber("+1234567890");
        user.setRole(Role.CLIENT);

        // Создаем response через конструктор со всеми аргументами
        response = new UserRegistrationResponse(
                1L,
                "John",
                "Doe",
                "john@example.com",
                "+1234567890",
                Role.CLIENT
        );
    }

    @ParameterizedTest
    @EnumSource(Role.class)
    void changeRole_WithAllRoles_ShouldChangeSuccessfully(Role newRole) {
        // given
        when(userDataRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userDataRepository.save(user)).thenReturn(user);

        UserRegistrationResponse expectedResponse = new UserRegistrationResponse(
                1L,
                "John",
                "Doe",
                "john@example.com",
                "+1234567890",
                newRole
        );
        when(userMapping.toResponseUserData(user)).thenReturn(expectedResponse);

        // when
        UserRegistrationResponse result = userRoleService.changeRole(1L, newRole);

        // then
        assertThat(result).isEqualTo(expectedResponse);
        assertThat(user.getRole()).isEqualTo(newRole);
        verify(userDataRepository).save(user);
    }

    @Test
    void changeRole_WhenUserExists_ShouldChangeRoleToAdminAndReturnResponse() {
        // given
        Role newRole = Role.ADMIN;
        when(userDataRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userDataRepository.save(user)).thenReturn(user);

        UserRegistrationResponse expectedResponse = new UserRegistrationResponse(
                1L,
                "John",
                "Doe",
                "john@example.com",
                "+1234567890",
                Role.ADMIN
        );
        when(userMapping.toResponseUserData(user)).thenReturn(expectedResponse);

        // when
        UserRegistrationResponse result = userRoleService.changeRole(1L, newRole);

        // then
        assertThat(result).isEqualTo(expectedResponse);
        assertThat(result.getRole()).isEqualTo(Role.ADMIN);
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
        verify(userDataRepository).save(user);
    }

    @Test
    void changeRole_WhenUserExists_ShouldChangeRoleToInstructorAndReturnResponse() {
        // given
        Role newRole = Role.INSTRUCTOR;
        when(userDataRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userDataRepository.save(user)).thenReturn(user);

        UserRegistrationResponse expectedResponse = new UserRegistrationResponse(
                1L,
                "John",
                "Doe",
                "john@example.com",
                "+1234567890",
                Role.INSTRUCTOR
        );
        when(userMapping.toResponseUserData(user)).thenReturn(expectedResponse);

        // when
        UserRegistrationResponse result = userRoleService.changeRole(1L, newRole);

        // then
        assertThat(result).isEqualTo(expectedResponse);
        assertThat(result.getRole()).isEqualTo(Role.INSTRUCTOR);
        assertThat(user.getRole()).isEqualTo(Role.INSTRUCTOR);
        verify(userDataRepository).save(user);
    }

    @Test
    void changeRole_WhenUserExists_ShouldChangeRoleToClientAndReturnResponse() {
        // given
        user.setRole(Role.ADMIN); // Начинаем с ADMIN
        Role newRole = Role.CLIENT;

        when(userDataRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userDataRepository.save(user)).thenReturn(user);

        UserRegistrationResponse expectedResponse = new UserRegistrationResponse(
                1L,
                "John",
                "Doe",
                "john@example.com",
                "+1234567890",
                Role.CLIENT
        );
        when(userMapping.toResponseUserData(user)).thenReturn(expectedResponse);

        // when
        UserRegistrationResponse result = userRoleService.changeRole(1L, newRole);

        // then
        assertThat(result).isEqualTo(expectedResponse);
        assertThat(result.getRole()).isEqualTo(Role.CLIENT);
        assertThat(user.getRole()).isEqualTo(Role.CLIENT);
        verify(userDataRepository).save(user);
    }

    @Test
    void changeRole_WhenUserNotFound_ShouldThrowException() {
        // given
        when(userDataRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userRoleService.changeRole(99L, Role.ADMIN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User with id 99 not found");

        verify(userDataRepository, never()).save(any());
        verify(userMapping, never()).toResponseUserData(any());
    }

    @Test
    void changeRole_WhenChangingToSameRole_ShouldWork() {
        // given
        Role sameRole = Role.CLIENT;
        when(userDataRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userDataRepository.save(user)).thenReturn(user);

        UserRegistrationResponse expectedResponse = new UserRegistrationResponse(
                1L,
                "John",
                "Doe",
                "john@example.com",
                "+1234567890",
                Role.CLIENT
        );
        when(userMapping.toResponseUserData(user)).thenReturn(expectedResponse);

        // when
        UserRegistrationResponse result = userRoleService.changeRole(1L, sameRole);

        // then
        assertThat(result).isEqualTo(expectedResponse);
        assertThat(result.getRole()).isEqualTo(Role.CLIENT);
        assertThat(user.getRole()).isEqualTo(Role.CLIENT);
        verify(userDataRepository).save(user);
    }

    @Test
    void changeRole_ShouldPreserveOtherUserFields() {
        // given
        Role newRole = Role.INSTRUCTOR;
        when(userDataRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userDataRepository.save(user)).thenReturn(user);

        UserRegistrationResponse expectedResponse = new UserRegistrationResponse(
                1L,
                "John",
                "Doe",
                "john@example.com",
                "+1234567890",
                Role.INSTRUCTOR
        );
        when(userMapping.toResponseUserData(user)).thenReturn(expectedResponse);

        // when
        UserRegistrationResponse result = userRoleService.changeRole(1L, newRole);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        assertThat(result.getPhoneNumber()).isEqualTo("+1234567890");
        assertThat(result.getRole()).isEqualTo(Role.INSTRUCTOR);
    }

    @Test
    void changeRole_WithNullRole_ShouldThrowException() {
        // given
        when(userDataRepository.findById(1L)).thenReturn(Optional.of(user));

        // when & then
        assertThatThrownBy(() -> userRoleService.changeRole(1L, null))
                .isInstanceOf(NullPointerException.class);

        verify(userDataRepository, never()).save(any());
        verify(userMapping, never()).toResponseUserData(any());
    }
}