package ru.skypro.homework.dto;

import lombok.Data;

/**
 * Класс `SecurityUserDto` представляет собой модель данных для пользователя системы безопасности.
 */
@Data
public class SecurityUserDto {
    private Integer id;//идентификатор пользователя
    private String email;//адрес электронной почты
    private String password;//пароль
    private Role role;//роль пользователя
}