package ru.stuff.authservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.stuff.authservice.dtos.account.ClaimsRequest;
import ru.stuff.authservice.dtos.gateway.AuthRequest;
import ru.stuff.authservice.dtos.gateway.AuthResponse;
import ru.stuff.authservice.dtos.gateway.RefreshRequest;
import ru.stuff.authservice.exceptions.AppError;
import ru.stuff.authservice.utils.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtTokenUtil jwtTokenUtil;

    public ResponseEntity<?> login(AuthRequest authRequest) {
        try {
            ResponseEntity<?> claimsRequest = restTemplate.postForEntity("http://account-service/account/get_user_claims", authRequest, ClaimsRequest.class);
            Map<String, Object> dataFromClaimsRequest = new HashMap<>();
            dataFromClaimsRequest.put("role", claimsRequest.getBody());
            Claims claims = Jwts.claims(dataFromClaimsRequest);

            String accessToken = jwtTokenUtil.generateAccessToken(authRequest.getEmail(), claims);
            String refreshToken = jwtTokenUtil.generateRefreshToken(authRequest.getEmail());

            AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_GATEWAY.value(), "Почта или пароль неверны"), HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<?> refresh(RefreshRequest refreshRequest) {
        try {
            Claims claims = jwtTokenUtil.getRefreshClaims(refreshRequest.getRefreshToken());

            if (!jwtTokenUtil.validateRefreshToken(refreshRequest.getRefreshToken())) {
                return new ResponseEntity<>(new AppError(HttpStatus.BAD_GATEWAY.value(), "Неверный токен обновления"), HttpStatus.BAD_GATEWAY);
            }

            String newAccessToken = jwtTokenUtil.generateAccessToken(claims.getSubject(), claims);
            String newRefreshToken = jwtTokenUtil.generateRefreshToken(claims.getSubject());

            AuthResponse authResponse = new AuthResponse(newAccessToken, newRefreshToken);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_GATEWAY.value(), "Ошибка обновления"), HttpStatus.BAD_GATEWAY);
        }
    }
}
