package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.LoginReq;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.service.AuthService;

import static ru.skypro.homework.dto.Role.USER;

/**
 * Класс `AuthController` является контроллером веб-приложения, который обрабатывает HTTP-запросы,
 * связанные с авторизацией и регистрацией пользователей
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //метод обрабатывает POST-запрос на авторизацию пользователя
    @Operation(
            operationId = "login",
            summary = "Авторизация пользователя",
            tags = {"Авторизация"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = Object.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // метод обрабатывает POST-запрос на регистрацию нового пользователя
    @Operation(
            operationId = "register",
            summary = "Регистрация пользователя",
            tags = {"Регистрация"},
            responses = {
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq req) {
        Role role = req.getRole() == null ? USER : req.getRole();

        if (authService.register(req, role)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}