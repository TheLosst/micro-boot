package ru.stuff.accountservice.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClaimsResponse {
    private String role;
}
