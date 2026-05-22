package org.example.repository;

import org.example.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserRegistration, Long> {

    UserRegistration findByEmail(String email);

    boolean existsByEmail(String email);
}
