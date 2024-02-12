package ru.thelosst.accountservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.thelosst.accountservice.dtos.auth.AuthRequest;
import ru.thelosst.accountservice.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/profile")
public class EditorController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest){
        return userService.createUser(authRequest);
    }

    @GetMapping("/hi")
    public String hi(){
        log.info("JJJ");
        return "JJJ";
    }
}
