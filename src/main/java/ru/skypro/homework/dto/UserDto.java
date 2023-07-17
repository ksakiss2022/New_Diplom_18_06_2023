package ru.skypro.homework.dto;

import lombok.Data;

/**
 * Класс `UserDto` представляет собой модель данных для пользователя.
 */
@Data
public class UserDto {
    private Integer id;//идентификатор пользователя
    private String email;//адрес электронной почты
    private String firstName;//имя пользователя
    private String lastName;//фамилия пользователя
    private String phone;//телефон пользователя
    private String image;//изображение пользователя
}