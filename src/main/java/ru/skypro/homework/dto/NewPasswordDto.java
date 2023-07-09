package ru.skypro.homework.dto;

import lombok.Data;
/**
 * Класс NewPasswordDto представляет собой класс отвечающий за текущий и новый пароль пользователя.
 */
@Data//Аннотация `@Data` автоматически генерирует методы `toString()`,
// `equals()`, `hashCode()`, `getter` и `setter` для всех полей класса.
public class NewPasswordDto {
    private String currentPassword;//строковое значение, представляющее собой текущий пароль для входа.
    private String newPassword;//строковое значение, представляющее собой новый пароль для входа.

//Метод `getPassword()` является публичным методом,который возвращает значение поля `newPassword` в виде объекта `CharSequence`.
    public CharSequence getPassword() {
        return newPassword;
    }
}
