package ru.thelosst.testservice.services;

import lombok.RequiredArgsConstructor;
import ru.thelosst.testservice.models.PC;
import ru.thelosst.testservice.repositories.PcRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PcService {

    private final PcRepository pcRepository;

    public List<PC> findAll(){
        return pcRepository.findAll();
    }

    public String findByTitle(String charfiled){
        Optional<PC> pc = pcRepository.findByTitle(charfiled);
        return pc.get().getTitle() + " Цена: " + pc.get().getPrice();
    }
}
