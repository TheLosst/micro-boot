package ru.thelosst.authservice.dtos.gateway;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
