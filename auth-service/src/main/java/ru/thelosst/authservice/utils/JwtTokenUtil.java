package ru.thelosst.authservice.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtil {
    // Значение секретного ключа доступа, полученное из application.properties
    @Value("${jwt.secret.access}")
    private String accessSecret;
    // Значение секретного ключа обновления, полученное из application\.properties
    @Value("${jwt.secret.refresh}")
    private String refreshSecret;

    /**
     * Генерирует токен доступа для указанного email и дополнительных claims.
     * Токен доступа действителен в течение 15 минут.
     *
     * @param email Адрес электронной почты пользователя
     * @param claims Дополнительные данные, которые будут храниться в токене
     * @return Строка токена доступа
     */
    @SuppressWarnings("deprecation")
    public String generateAccessToken(String email, Claims claims) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(15).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        claims.put("exp", accessExpiration);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS256, accessSecret)
                .setClaims(claims)
                .compact();
    }

    /**
     * Генерирует токен обновления для указанного email.
     * Токен обновления действителен в течение 30 дней.
     *
     * @param email Адрес электронной почты пользователя
     * @return Строка токена обновления
     */
    @SuppressWarnings("deprecation")
    public String generateRefreshToken(String email) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(refreshExpiration)
                .signWith(SignatureAlgorithm.HS256, refreshSecret)
                .compact();
    }

    /**
     * Проверяет валидность токена доступа.
     *
     * @param accessToken Токен доступа
     * @return true, если токен валидный, false - иначе
     */
    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessSecret);
    }

    /**
     * Проверяет валидность токена обновления.
     *
     * @param refreshToken Токен обновления
     * @return true, если токен валидный, false - иначе
     */
    @SuppressWarnings("deprecation")
    private boolean validateToken(String token, String secret) {
        try {
            // Создаем парсер токенов с установленным секретным ключом.
            Jwts.parserBuilder()
                   .setSigningKey(getSignInKey(secret))
                   .build()
                    .parseClaimsJws(token);
             // Если парсинг прошел успешно, значит токен валидный.
            return true;
        } catch (ExpiredJwtException expEx) {
             // Токен истек, логируем ошибку.
             log.error("Токен истек", expEx);
        } catch (UnsupportedJwtException unsEx) {
            // Неподдерживаемый формат токена, логируем ошибку.
            log.error("Неподдерживаемый формат JWT", unsEx);
        } catch (MalformedJwtException mjEx) {
            // Токен имеет неправильную структуру, логируем ошибку.
            log.error("Некорректно сформированный JWT", mjEx);
        } catch (SignatureException sEx) {
            // Неверная подпись токена, логируем ошибку.
            log.error("Неверная подпись", sEx);
        } catch (Exception e) {
            // Общая ошибка обработки токена, логируем ошибку.
            log.error("Невалидный токен", e);
        }
    // Если возникла любая ошибка, токен невалидный.
    return false;
}

/**
 * Получает claims из токена с использованием секретного ключа.
 * 
 * @param token Токен для извлечения claims.
 * @param secret Секретный ключ для подписи токена.
 * @return claims из токена, null при ошибке.
 */
private Claims getClaims(String token, String secret) {
    try {
        // Создаем парсер токенов с установленным секретным ключом.
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    } catch (Exception e) {
        // Ошибка при извлечении claims, возвращаем null.
        log.error("Ошибка при получении claims из токена", e);
        return null;
    }
}

/**
 * Получает ключ для подписи токена из секретной строки.
 * 
 * @param secret Секретная строка для создания ключа.
 * @return Ключ для подписи токена.
 */
private Key getSignInKey(String secret) {
    // Декодируем секретную строку из Base64.
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    // Создаем ключ для подписи на основе декодированной строки.
    return Keys.hmacShaKeyFor(keyBytes);
}

/**
 * Получает claims из токена доступа.
 * 
 * @param token Токен доступа.
 * @return claims из токена доступа, null при ошибке.
 */
public Claims getAccessClaims(String token) {
    return getClaims(token, accessSecret);
}

/**
 * Получает claims из токена обновления.
 * 
 * @param token Токен обновления.
 * @return claims из токена обновления, null при ошибке.
 */
public Claims getRefreshClaims(String token) {
    return getClaims(token, refreshSecret);
}
}
