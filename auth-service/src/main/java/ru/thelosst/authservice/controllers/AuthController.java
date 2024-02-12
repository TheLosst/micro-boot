package ru.thelosst.authservice.controllers;

import lombok.AllArgsConstructor;
import ru.thelosst.authservice.dtos.gateway.AuthRequest;
import ru.thelosst.authservice.dtos.gateway.RefreshRequest;
import ru.thelosst.authservice.services.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        return authService.login(authRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest){
        return authService.refresh(refreshRequest);
    }

    @GetMapping("/test")
    public String test(){
        return "Auth Service is Started!";
    }
}
