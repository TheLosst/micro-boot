package ru.thelosst.apigateway.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.thelosst.apigateway.filter.AuthenticationFilter;

@Configuration // Помечает класс как конфигурационный bean Spring
public class RouteConfig {

    @Autowired // Поле вводится автоматически Spring из контекста приложения
    private AuthenticationFilter filter;

    @Bean // Создает bean Spring и возвращает его
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        // Строит набор маршрутов с использованием RouteLocatorBuilder
        return builder.routes()
                .route(
                        // Маршрут "account-service"
                        "account-service",
                        p -> p
                                // Путь для маршрута: /profile/**
                                .path("/profile/**")
                                // Применяет фильтр аутентификации ко всем запросам
                                .filters(f -> f.filter(filter))
                                // Перенаправляет запросы на сервис account-service (lb://account-service)
                                .uri("lb://account-service")
                )
                .route(
                        // Маршрут "auth-service"
                        "auth-service",
                        p -> p
                                // Путь для маршрута: /auth/**
                                .path("/auth/**")
                                // Применяет фильтр аутентификации ко всем запросам
                                .filters(f -> f.filter(filter))
                                // Перенаправляет запросы на сервис auth-service (lb://auth-service)
                                .uri("lb://auth-service")
                )
                .build();
    }
}
