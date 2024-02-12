package ru.thelosst.accountservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.thelosst.accountservice.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
}
