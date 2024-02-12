package ru.thelosst.pcservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.thelosst.pcservice.models.PC;

import java.util.Optional;

public interface PcRepository extends JpaRepository<PC, Long> {
    Optional<PC> findByTitle(String title);
}
