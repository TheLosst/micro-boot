package ru.thelosst.testservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.thelosst.testservice.models.Test;
import ru.thelosst.testservice.services.TestService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class Controller {

    private final TestService testService;

    @GetMapping("/test")
    public String test(){
        log.info("Hello");
        return "HELLO from CVYLEV-SERVICE";
    }

    @GetMapping("/showAll")
    public List<Test> showAll(){
        return testService.findAll();
    }

    @GetMapping("/get_price")
    public String showAll(@RequestParam String title){
        return testService.findByTitle(title);
    }
}
