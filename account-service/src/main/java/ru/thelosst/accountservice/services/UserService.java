package ru.thelosst.accountservice.services;

import lombok.RequiredArgsConstructor;
import ru.thelosst.accountservice.dtos.auth.AuthRequest;
import ru.thelosst.accountservice.dtos.auth.ClaimsResponse;
import ru.thelosst.accountservice.exceptions.AppError;
import ru.thelosst.accountservice.models.Role;
import ru.thelosst.accountservice.models.User;
import ru.thelosst.accountservice.repositories.RoleRepository;
import ru.thelosst.accountservice.repositories.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> sendClaims(AuthRequest authRequest) {
        try {
            User user = userRepository.findByEmail(authRequest.getEmail()).get();
            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                Role role = roleRepository.findById(user.getRole().getId()).get();
                ClaimsResponse claimsResponse = new ClaimsResponse(role.getTag());
                return ResponseEntity.ok(claimsResponse);
            } else {
                return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Неверный email или пароль"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_GATEWAY.value(), "Ошибка авторизации"), HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<?> createUser(AuthRequest authRequest) {
        try {
            if (!isValidEmail(authRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AppError(HttpStatus.BAD_REQUEST.value(), "Неверный формат email"));
            }
            if (!isValidPassword(authRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароль не соответствует требованиям"));
            }
            if (userRepository.findByEmail(authRequest.getEmail()).isEmpty()) {
                User user = new User();
                user.setEmail(authRequest.getEmail());
                user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
                user.setRole(roleRepository.findByTag("ROLE_USER").get());
                return ResponseEntity.ok(userRepository.save(user));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new AppError(HttpStatus.CONFLICT.value(), "Пользователь с таким email уже существует"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка создания пользователя"));
        }
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}