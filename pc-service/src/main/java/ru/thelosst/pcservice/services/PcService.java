package ru.thelosst.pcservice.services;

import lombok.RequiredArgsConstructor;
import ru.thelosst.pcservice.models.PC;
import ru.thelosst.pcservice.repositories.PcRepository;

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

    public String findByTitle(String title){
        Optional<PC> pc = pcRepository.findByTitle(title);
        return pc.get().getTitle() + " Цена: " + pc.get().getPrice();
    }
}
