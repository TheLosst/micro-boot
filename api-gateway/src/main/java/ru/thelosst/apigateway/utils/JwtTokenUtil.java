package ru.thelosst.apigateway.utils;

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

    // ПРИВАТНЫЕ МЕТОДЫ

    /**
     * Извлекает информацию (claims) из токена.
     *
     * @param token Строка токена
     * @return Информация из токена, или null при ошибке
     */
    private Claims getClaims(String token) {
        try {
            // Создает парсер токенов с установленным секретным ключом доступа
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey(accessSecret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // Ошибка при извлечении информации, возвращаем null
            log.error("Ошибка при получении информации из токена", e);
            return null;
        }
    }

    /**
     * Проверяет, истек ли срок действия токена.
     *
     * @param token Строка токена
     * @return true, если токен истек, false - иначе
     */
    private boolean isTokenExpired(String token) {
        // Извлекает информацию из токена и сравнивает дату истечения с текущей
        return this.getClaims(token).getExpiration().before(new Date());
    }

    /**
     * Проверяет, является ли токен невалидным (истек срок действия или другая ошибка).
     *
     * @param token Строка токена
     * @return true, если токен невалидный, false - иначе
     */
    public boolean isInvalid(String token) {
        // Делегирует проверку на истечение срока действия
        return this.isTokenExpired(token);
    }

    /**
     * Получает ключ подписи из секретной строки.
     *
     * @param secret Секретная строка
     * @return Ключ подписи
     */
    private Key getSignInKey(String secret) {
        // Декодирует секретную строку из Base64
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        // Создает ключ подписи на основе декодированной строки
        return Keys.hmacShaKeyFor(keyBytes);
    }
}