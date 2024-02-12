package ru.stuff.authservice.dtos.gateway;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}
