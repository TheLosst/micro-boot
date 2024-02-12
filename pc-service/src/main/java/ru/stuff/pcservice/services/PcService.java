package ru.stuff.pcservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stuff.pcservice.models.PC;
import ru.stuff.pcservice.repositories.PcRepository;

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
