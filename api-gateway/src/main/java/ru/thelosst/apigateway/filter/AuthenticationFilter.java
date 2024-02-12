package ru.thelosst.apigateway.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.thelosst.apigateway.route.RouterValidator;
import ru.thelosst.apigateway.utils.JwtTokenUtil;

@Component
public class AuthenticationFilter implements GatewayFilter {

    @Autowired
    private RouterValidator routerValidator;  // Валидатор маршрутов для проверки защищенности

    @Autowired
    private JwtTokenUtil jwtTokenUtil;  // Утилита для работы с JWT токенами

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Проверка, требует ли текущий маршрут аутентификацию
        if (routerValidator.isSecured.test(request)) {
            // Проверка наличия авторизационных данных в заголовке
            if (isAuthMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED); // Ответ об отсутствии авторизации
            }

            // Извлечение токена авторизации из заголовка
            final String token = getAuthHeader(request);

            // Проверка валидности токена с помощью JwtTokenUtil
            if (jwtTokenUtil.isInvalid(token)) {
                return onError(exchange, HttpStatus.FORBIDDEN); // Ответ о невалидном токене
            }

            // Добавление информации из токена в запрос
            updateRequest(exchange, token);
        }

        // Передача управления по цепочке фильтров
        return chain.filter(exchange);
    }

    // ****************************************** ПРИВАТНЫЕ МЕТОДЫ *******************************************

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return authHeader;
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void updateRequest(ServerWebExchange exchange, String token) {
        Claims claims = jwtTokenUtil.getClaims(token);
        exchange.getRequest().mutate()
                .header("email", String.valueOf(claims.get("email"))) // Добавление email из токена в запрос
                .build();
    }
}
