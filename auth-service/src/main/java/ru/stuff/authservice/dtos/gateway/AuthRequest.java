package ru.stuff.authservice.dtos.gateway;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
