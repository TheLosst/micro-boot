package ru.thelosst.testservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.thelosst.testservice.models.PC;

import java.util.Optional;

public interface PcRepository extends JpaRepository<PC, Long> {
    Optional<PC> findByTitle(String charfiled);
}
