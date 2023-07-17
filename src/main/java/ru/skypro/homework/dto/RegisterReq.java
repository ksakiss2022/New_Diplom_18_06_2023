package ru.skypro.homework.dto;

import lombok.Data;

/**
 * Класс `RegisterReq` представляет собой модель данных для запроса регистрации нового пользователя.
 */
@Data
public class RegisterReq {
    private String username;//имя пользователя
    private String password;//пароль
    private String firstName;//имя)
    private String lastName;//фамилия
    private String phone;//телефон
    private Role role;//роль пользователя)
}
