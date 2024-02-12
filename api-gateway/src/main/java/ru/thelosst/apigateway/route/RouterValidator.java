package ru.thelosst.apigateway.route;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    // Список открытых API-эндопойнтов, требующих аутентификации
    public static final List<String> openApiEndpoints = List.of(
            "/auth/login",
            "/profile/register"
    );

    /**
     * Предикат для проверки, требует ли запрос аутентификации.
     *
     * @param request Запрос, который нужно проверить.
     * @return true, если запрос открытый (не требует аутентификации), false - иначе.
     */
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
                    // Проверяет, не совпадает ли путь запроса с любым из открытых API-эндопойнтов

}
