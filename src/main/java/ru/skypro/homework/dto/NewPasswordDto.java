package ru.skypro.homework.dto;

import lombok.Data;

/**
 * Класс `NewPasswordDto` представляет собой модель данных для запроса изменения пароля пользователя.
 * Он содержит два приватных поля: `currentPassword` (текущий пароль) и `newPassword` (новый пароль).
 */
@Data
public class NewPasswordDto {
    private String currentPassword;
    private String newPassword;

    //возвращает новый пароль в виде объекта `CharSequence`.
    public CharSequence getPassword() {
        return newPassword;
    }
}
