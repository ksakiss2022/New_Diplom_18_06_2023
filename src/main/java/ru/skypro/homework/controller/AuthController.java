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
import ru.skypro.homework.service.UserService;

import static ru.skypro.homework.dto.Role.USER;

/**
 * Класс AuthController является контроллером, который отвечает за обработку HTTP-запросов,
 * связанных с аутентификацией и авторизацией пользователей. Он содержит два метода,
 * которые позволяют пользователю проходить процесс регистрации и авторизационного входа.
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;


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
    /**
     *  login: Метод для выполнения авторизационного входа пользователя. Принимает объект LoginReq,
     *  содержащий имя пользователя и пароль. Возвращает ResponseEntity без тела ответа.
     *  Если авторизационный вход успешный, возвращается статус OK (200). В случае, если имя
     *  пользователя или пароль неправильные, возвращается статус UNAUTHORIZED (401).
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


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
    /**
     * register: Метод для регистрации нового пользователя. Принимает объект RegisterReq,
     * содержащий информацию о новом пользователе, такую как имя, пароль и роль.
     * Если роль не указана, по умолчанию устанавливается роль USER. Возвращает ResponseEntity
     * без тела ответа. Если регистрация успешная, возвращается статус CREATED (201).
     * Если регистрация не удалась, возвращается статус FORBIDDEN (403).
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq req) {
        Role role = req.getRole() == null ? USER : req.getRole();

        if (authService.register(req, role)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}