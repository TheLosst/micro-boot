package ru.thelosst.accountservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.thelosst.accountservice.dtos.auth.AuthRequest;
import ru.thelosst.accountservice.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Аннотация для внедрения логгера SLF4j
@Slf4j

// Аннотация для обозначения REST контроллера
@RestController

// Аннотация для автоматической инициализации всех полей класса в конструкторе
@AllArgsConstructor

// Аннотация для маппинга запросов к данному контроллеру на URL-путь "/profile"
@RequestMapping("/profile")
public class EditorController {

   // Поле для сервиса работы с пользователями
   private final UserService userService;

   // Обработчик POST-запроса на регистрацию пользователя
   @PostMapping("/register")
   public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
       // Вызов метода сервиса для создания пользователя и возврат результата
       return userService.createUser(authRequest);
   }

   // Обработчик GET-запроса на проверку токена
   @GetMapping("/hi")
   public String hi() {
       // Запись информационного сообщения в лог
       log.info("AuthToken correct");
       // Возврат сообщения об успешности проверки токена
       return "AuthToken correct";
   }
}