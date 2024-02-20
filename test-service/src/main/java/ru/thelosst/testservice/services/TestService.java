package ru.thelosst.testservice.services;

import lombok.RequiredArgsConstructor;
import ru.thelosst.testservice.models.Test;
import ru.thelosst.testservice.repositories.TestRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public List<Test> findAll(){
        return testRepository.findAll();
    }

    public String findByTitle(String charfiled){
        Optional<Test> pc = testRepository.findByTitle(charfiled);
        return pc.get().getTitle() + " Цена: " + pc.get().getPrice();
    }
}
