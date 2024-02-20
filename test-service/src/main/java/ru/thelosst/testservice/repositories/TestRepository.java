package ru.thelosst.testservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.thelosst.testservice.models.Test;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findByTitle(String charfiled);
}
