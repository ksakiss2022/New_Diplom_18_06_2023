package ru.skypro.homework.dto;

import lombok.Data;

/**
 * Класс `LoginReq` представляет собой модель данных для запроса аутентификации пользователя.
 * Он содержит два приватных поля: `password` (пароль) и `username` (имя пользователя).
 */
@Data
public class LoginReq {
    private String password;
    private String username;

}