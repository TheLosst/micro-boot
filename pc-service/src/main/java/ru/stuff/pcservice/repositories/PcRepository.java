package ru.stuff.pcservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stuff.pcservice.models.PC;

import java.util.Optional;

public interface PcRepository extends JpaRepository<PC, Long> {
    Optional<PC> findByTitle(String title);
}
