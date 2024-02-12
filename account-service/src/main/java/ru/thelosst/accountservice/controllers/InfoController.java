package ru.thelosst.accountservice.controllers;

import lombok.AllArgsConstructor;
import ru.thelosst.accountservice.dtos.auth.AuthRequest;
import ru.thelosst.accountservice.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class InfoController {

    private final UserService userService;

    /**
     * Обработка POST запроса на получение списка прав доступа пользователя.
     *
     * @param authRequest Объект запроса содержащий данные для авторизации (логин и пароль пользователя).
     * @return ResponseEntity<?> Объект ответа сервера, содержащий данные о правах доступа пользователя
     * или ошибку, если авторизация не прошла успешно.
     */
    @PostMapping("/get_user_claims")
    public ResponseEntity<?> sendClaimsUser(@RequestBody AuthRequest authRequest){
        return userService.sendClaims(authRequest);
    }
}