package ru.stuff.accountservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stuff.accountservice.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByTag(String tag);
    Optional<Role> findById(Long id);
}
