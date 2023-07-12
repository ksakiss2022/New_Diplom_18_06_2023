package ru.skypro.homework.dto;

import lombok.Data;

/**
 * Класс v представляет собой класс для регистрации кода для входа.
 */
@Data //Аннотация `@Data` является частью библиотеки Lombok и автоматически генерирует
// методы `toString()`, `equals()`, `hashCode()`, `getter` и `setter` для всех полей класса.
public class LoginReq {
    private String password;//строковое значение, представляющее пароль для входа.
    private String username;// строковое поле, представляющее имя пользователя.

}